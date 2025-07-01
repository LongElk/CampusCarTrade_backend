package org.example.campuscartrade.controller;

import org.example.campuscartrade.pojo.Entity.Order;

import org.example.campuscartrade.service.OrderService;
import org.example.campuscartrade.service.UserService;
import org.example.campuscartrade.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired private OrderService orderService;
    @Autowired private UserService userService;
    @Autowired private VehicleService vehicleService;

    // 1. 买家下单
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> body) {
        Long buyerId  = ((Number) body.get("buyerId")).longValue();
        Long vehicleId = ((Number) body.get("vehicleId")).longValue();

        var buyer = userService.getById(buyerId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "买家不存在"));
        var vehicle = vehicleService.getById(vehicleId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "车辆不存在"));

        // 卖家即车辆的 owner
        var seller = vehicle.getSeller();

        Order order = new Order();
        order.setBuyer(buyer);
        order.setSeller(seller);
        order.setVehicle(vehicle);
        // status 默认 PENDING

        Order saved = orderService.createOrder(order);
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "message", "下单成功",
                "data", Map.of("orderId", saved.getId())
        ));
    }

    // 2. 买家查看自己的订单列表
    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<?> listBuyerOrders(@PathVariable Long buyerId) {
        var orders = orderService.getBuyerOrder(buyerId);
        return ResponseEntity.ok(Map.of("code", 200, "data", orders));
    }

    // 3. 卖家查看自己的接单列表
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<?> listSellerOrders(@PathVariable Long sellerId) {
        var orders = orderService.getSellerOrder(sellerId);
        return ResponseEntity.ok(Map.of("code", 200, "data", orders));
    }

    // 4. 更新订单状态（卖家同意或拒绝）
    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long orderId,
            @RequestBody Map<String, String> body) {
        String newStatus = body.get("status");
        // 只允许 CONFIRMED 或 CANCELLED
        if (!List.of("CONFIRMED","CANCELLED").contains(newStatus)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "无效的状态");
        }

        Order updated = orderService.updateOrderStatus(orderId, newStatus);
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "message", "更新成功",
                "data", updated
        ));
    }

    // 5. 删除订单（可选）
    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok(Map.of("code", 200, "message", "删除成功"));
    }
}

