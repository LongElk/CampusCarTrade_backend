package org.example.campuscartrade.controller;

import org.example.campuscartrade.pojo.Entity.Order;
import org.example.campuscartrade.pojo.Entity.User;
import org.example.campuscartrade.pojo.Entity.Vehicle;
import org.example.campuscartrade.pojo.VO.OrderVO;
import org.example.campuscartrade.service.OrderService;
import org.example.campuscartrade.service.UserService;
import org.example.campuscartrade.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Map<String, Object> payload) {
        Long buyerId = Long.valueOf(payload.get("buyerId").toString());
        Long vehicleId = Long.valueOf(payload.get("vehicleId").toString());

        User buyer = userService.getById(buyerId).orElseThrow(() -> new RuntimeException("买家不存在"));
        Vehicle vehicle = vehicleService.getById(vehicleId).orElseThrow(() -> new RuntimeException("车辆不存在"));
        User seller = vehicle.getSeller();

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

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Map<String, Object>> updateStatus(@PathVariable Long orderId, @RequestBody Map<String, Object> payload) {
        String statusStr = payload.get("status").toString();
        Order.Status status = Order.Status.valueOf(statusStr.toUpperCase());

        Order updated = orderService.updateOrderStatus(orderId, status.name());

        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("message", "状态更新成功");
        res.put("data", OrderVO.convertToVO(updated));
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

    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<OrderVO>> getBuyerOrders(@PathVariable Long buyerId) {
        List<OrderVO> voList = orderService.getBuyerOrder(buyerId)
                .stream().map(OrderVO::convertToVO).collect(Collectors.toList());
        return ResponseEntity.ok(voList);
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<OrderVO>> getSellerOrders(@PathVariable Long sellerId) {
        List<OrderVO> voList = orderService.getSellerOrder(sellerId)
                .stream().map(OrderVO::convertToVO).collect(Collectors.toList());
        return ResponseEntity.ok(voList);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<OrderVO>> getVehicleOrders(@PathVariable Long vehicleId) {
        List<OrderVO> voList = orderService.getVehicleOrder(vehicleId)
                .stream().map(OrderVO::convertToVO).collect(Collectors.toList());
        return ResponseEntity.ok(voList);
    }
}


