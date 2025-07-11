package org.example.campuscartrade.controller;

import org.example.campuscartrade.context.BaseContext;
import org.example.campuscartrade.pojo.Entity.Image;
import org.example.campuscartrade.pojo.Entity.User;
import org.example.campuscartrade.pojo.Entity.Vehicle;
import org.example.campuscartrade.pojo.VO.*;
import org.example.campuscartrade.service.ImageService;
import org.example.campuscartrade.service.UserService;
import org.example.campuscartrade.service.VehicleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.example.campuscartrade.pojo.Entity.Vehicle.Status.AVAILABLE;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin(origins = "*")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;
    // 发布车辆
    @Transactional
    @PostMapping
    public ResponseEntity<Map<String, Object>> publishVehicle(@RequestBody Map<String, Object> payload) {
        Long sellerId = BaseContext.getCurrentId();
        User seller = userService.getById(sellerId).orElse(null);
        if (seller == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("code", 400);
            error.put("message", "卖家不存在");
            return ResponseEntity.badRequest().body(error);
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setSeller(seller);
        vehicle.setTitle((String) payload.get("title"));
        vehicle.setType(Vehicle.Type.valueOf((String) payload.get("type")));
        vehicle.setDescription((String) payload.get("description"));
        vehicle.setPrice(Double.valueOf(payload.get("price").toString()));
        vehicle.setMileage(payload.get("mileage") != null ? ((Number) payload.get("mileage")).intValue() : null);
        vehicle.setLocation((String) payload.get("location"));
        vehicle.setPublishTime(LocalDateTime.now());
        vehicle.setStatus(Vehicle.Status.AVAILABLE); // 默认上架为在售

        Vehicle saved = vehicleService.publish(vehicle);
        List<String> urls = (List<String>) payload.get("imageUrls");
        int index = 0;
        for (String url : urls) {
            imageService.save(saved.getId(),url,index);
            index++;
        }
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("message", "发布成功");
        res.put("data", Map.of("vehicleId", saved.getId()));
        return ResponseEntity.ok(res);
    }

    // 获取车辆列表（可加筛选）
    @GetMapping
    public ResponseEntity<Map<String, Object>> listVehicles(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        Vehicle.Type typeEnum = null;
        Vehicle.Status statusEnum = null;

        if (type != null) {
            try {
                typeEnum = Vehicle.Type.valueOf(type);
            } catch (IllegalArgumentException ignored) {}
        }

        if (status != null) {
            try {
                statusEnum = Vehicle.Status.valueOf(status);
            } catch (IllegalArgumentException ignored) {}
        }

        PageResult<Vehicle> pageResult = vehicleService.queryVehicles(typeEnum, statusEnum, keyword,minPrice,maxPrice,page, size);
        List<Vehicle> list = pageResult.getList();
        List<VehiclePage> vehiclePages = new ArrayList<>(list.size());
        for (Vehicle vehicle : list) {
            VehiclePage vehiclePage = new VehiclePage();
            BeanUtils.copyProperties(vehicle,vehiclePage);
            List<Image> images = imageService.getByVehicleId(vehicle.getId());
            String url;
            if (images == null || images.size() == 0){
                url = "";
            }else {
                url = imageService.getByVehicleId(vehicle.getId()).get(0).getUrl();
            }
            vehiclePage.setImageUrl(url);
            vehiclePages.add(vehiclePage);
        }

        List<Map<String, Object>> items = vehiclePages.stream().map(vehicle -> {
            Map<String, Object> map = new HashMap<>();
            map.put("title", vehicle.getTitle());
            map.put("price", vehicle.getPrice());
            map.put("id",vehicle.getId());
            map.put("imageUrl",vehicle.getImageUrl()); // 示例
            return map;
        }).toList();

        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("message", "获取成功");
        res.put("data", Map.of(
                "total", pageResult.getTotal(),
                "page", page,
                "size", size,
                "items", items
        ));

        return ResponseEntity.ok(res);
    }

    // 获取车辆详情
    @GetMapping("/{vehicleId}")
    public ResponseEntity<Map<String, Object>> getDetail(@PathVariable Long vehicleId) {
        Vehicle vehicle = vehicleService.getById(vehicleId).orElse(null);
        List<Image> imges = imageService.getByVehicleId(vehicleId);
        List<ImageVO> imageVOS = new ArrayList<>();
        for (Image imge : imges) {
            ImageVO imageVO = new ImageVO();
            BeanUtils.copyProperties(imge,imageVO);
            imageVOS.add(imageVO);
        }
        Map<String, Object> res = new HashMap<>();
        if (vehicle == null) {
            res.put("code", 404);
            res.put("message", "车辆不存在");
            return ResponseEntity.status(404).body(res);
        }
        User seller = userService.getById(vehicle.getSeller().getId()).orElseThrow(() -> new RuntimeException("卖家不存在"));
        SellerVO sellerVO = new SellerVO();
        BeanUtils.copyProperties(seller,sellerVO);
        VehicleVO vehicleVO = new VehicleVO();
        BeanUtils.copyProperties(vehicle,vehicleVO);
        vehicleVO.setImages(imageVOS);
        vehicleVO.setSellerVO(sellerVO);
        res.put("code", 200);
        res.put("message", "获取成功");
        res.put("data", vehicleVO);
        return ResponseEntity.ok(res);
    }

    // 更新车辆状态
    @PutMapping("/{vehicleId}/status")
    public ResponseEntity<Map<String, Object>> updateStatus(@PathVariable Long vehicleId, @RequestBody Map<String, String> body) {
        String status = body.get("status");
        Vehicle updated = vehicleService.updateStatus(vehicleId, status);
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("message", "更新成功");
        res.put("data", updated);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/my")
    public ResponseEntity<Map<String, Object>> getMyVehicles(@RequestParam Long userId) {
        List<Vehicle> vehicles = vehicleService.getBySellerId(userId);
        List<VehicleVO> vehicleVOS = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            VehicleVO vehicleVO = new VehicleVO();
            BeanUtils.copyProperties(vehicle, vehicleVO);
            // 组装图片
            List<ImageVO> imageVOS = new ArrayList<>();
            List<Image> images = imageService.getByVehicleId(vehicle.getId());
            for (Image image : images) {
                ImageVO imageVO = new ImageVO();
                BeanUtils.copyProperties(image, imageVO);
                imageVOS.add(imageVO);
            }
            vehicleVO.setImages(imageVOS);
            // 组装卖家信息
            User seller = userService.getById(vehicle.getSeller().getId()).orElse(null);
            if (seller != null) {
                SellerVO sellerVO = new SellerVO();
                sellerVO.setId(seller.getId());
                sellerVO.setName(seller.getName());
                sellerVO.setPhone(seller.getPhone());
                vehicleVO.setSellerVO(sellerVO);
            }
            vehicleVOS.add(vehicleVO);
        }
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("message", "查询成功");
        res.put("data", vehicleVOS);
        return ResponseEntity.ok(res);
    }
}

