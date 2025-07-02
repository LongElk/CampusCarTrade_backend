package org.example.campuscartrade.controller;

import org.example.campuscartrade.context.BaseContext;
import org.example.campuscartrade.pojo.Entity.Image;
import org.example.campuscartrade.pojo.Entity.Order;
import org.example.campuscartrade.pojo.Entity.User;
import org.example.campuscartrade.pojo.Entity.Vehicle;
import org.example.campuscartrade.pojo.VO.OrderVO;
import org.example.campuscartrade.pojo.VO.VehiclePage;
import org.example.campuscartrade.pojo.VO.VehicleVO;
import org.example.campuscartrade.service.ImageService;
import org.example.campuscartrade.service.OrderService;
import org.example.campuscartrade.service.UserService;
import org.example.campuscartrade.service.VehicleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.example.campuscartrade.pojo.Entity.Vehicle.Status.SOLD;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ImageService imageService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Map<String, Object> payload) {
        Long buyerId = BaseContext.getCurrentId();
        Long vehicleId = Long.valueOf(payload.get("vehicleId").toString());

        User buyer = userService.getById(buyerId).orElseThrow(() -> new RuntimeException("买家不存在"));
        Vehicle vehicle = vehicleService.getById(vehicleId).orElseThrow(() -> new RuntimeException("车辆不存在"));
        User seller = vehicle.getSeller();
        vehicle.setStatus(SOLD);
        if (vehicle.getPrice() == null) {
            throw new RuntimeException("车辆价格为空");
        }

        Order order = new Order();
        order.setBuyer(buyer);
        order.setSeller(seller);
        order.setVehicle(vehicle);
        order.setPrice(vehicle.getPrice());

        Order saved = orderService.createOrder(order);
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("message", "下单成功");
        res.put("data", saved.getId());
        return ResponseEntity.ok(res);
    }

    @PutMapping("/status")
    public ResponseEntity<Map<String, Object>> updateStatus( @RequestBody Map<String, Object> payload) {
        String statusStr = payload.get("status").toString();
        Order.Status status = Order.Status.valueOf(statusStr.toUpperCase());
        Long  orderId = BaseContext.getCurrentId();
        Order updated = orderService.updateOrderStatus(orderId, status.name());
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(updated,orderVO);
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("message", "状态更新成功");
        res.put("data",orderVO);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Map<String, Object>> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("message", "订单已删除");
        return ResponseEntity.ok(res);
    }

    @GetMapping("/buyer")
    public ResponseEntity<Map<String, Object>> getBuyerOrders() {
        Long  buyerId = BaseContext.getCurrentId();
        List<Order> orders = orderService.getBuyerOrder(buyerId);
        User buyer = userService.getById(buyerId).orElseThrow(() -> new RuntimeException("买家不存在"));
        List<OrderVO> voList = new ArrayList<>(orders.size());
        for (Order order : orders) {
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(order,orderVO);
            User seller = userService.getById(order.getSeller().getId()).orElseThrow(() -> new RuntimeException("买家不存在"));
            Vehicle vehicle = vehicleService.getById(order.getVehicle().getId()).orElse(null);
            VehiclePage vehiclePage = new VehiclePage();
            BeanUtils.copyProperties(vehicle,vehiclePage);
            List<Image> images = imageService.getByVehicleId(vehicle.getId());
            String url;
            if (images == null || images.size() == 0){
                url = "";
            }else {
                url = images.get(0).getUrl();
            }
            vehiclePage.setImageUrl(url);
            orderVO.setVehicle(vehiclePage);
            orderVO.setBuyerName(buyer.getName());
            orderVO.setSellerName(seller.getName());
            voList.add(orderVO);
        }
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("message", "获取订单成功");
        res.put("data", Map.of(
                "total",voList.size(),
                "items", voList
        ));
        return ResponseEntity.ok(res);
    }

    @GetMapping("/seller")
    public ResponseEntity<Map<String, Object>> getSellerOrders() {
        Long  sellerId = BaseContext.getCurrentId();
        List<Order> orders = orderService.getSellerOrder(sellerId);
        User seller = userService.getById(sellerId).orElseThrow(() -> new RuntimeException("卖家不存在"));
        List<OrderVO> voList = new ArrayList<>(orders.size());
        for (Order order : orders) {
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(order,orderVO);
            User buyer = userService.getById(order.getBuyer().getId()).orElseThrow(() -> new RuntimeException("买家不存在"));
            Vehicle vehicle = vehicleService.getById(order.getVehicle().getId()).orElse(null);
            VehiclePage vehiclePage = new VehiclePage();
            BeanUtils.copyProperties(vehicle,vehiclePage);
            List<Image> images = imageService.getByVehicleId(vehicle.getId());
            String url;
            if (images == null || images.size() == 0){
                url = "";
            }else {
                url = images.get(0).getUrl();
            }
            vehiclePage.setImageUrl(url);
            orderVO.setVehicle(vehiclePage);
            orderVO.setBuyerName(buyer.getName());
            orderVO.setSellerName(seller.getName());
            voList.add(orderVO);
        }
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("message", "获取订单成功");
        res.put("data", Map.of(
                "total",voList.size(),
                "items", voList
        ));
        return ResponseEntity.ok(res);
    }
}


