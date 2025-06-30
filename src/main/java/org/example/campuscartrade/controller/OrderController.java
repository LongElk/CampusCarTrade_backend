package org.example.campuscartrade.controller;

import org.example.campuscartrade.pojo.Entity.Order;
import org.example.campuscartrade.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 获取订单列表（支持分页、角色和状态筛选）
    @GetMapping
    public ResponseEntity<Map<String, Object>> getOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) Order.Status status) {
        Map<String, Object> orders = orderService.getOrders(page, size, role, status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // 根据ID获取订单详情
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(order -> new ResponseEntity<>(order, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 创建订单
    @PostMapping
    public ResponseEntity<Map<String, Object>> createOrder(
            @RequestBody Map<String, Long> orderInfo) {
        Map<String, Object> createdOrder = orderService.createOrder(new Order(),
                orderInfo.get("buyerId"),orderInfo.get("vehicleId"));
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    // 更新订单状态
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> statusInfo) {
        Order.Status status = Order.Status.valueOf(statusInfo.get("status"));
        return orderService.updateOrderStatus(id, status)
                .map(updatedOrder -> new ResponseEntity<>(updatedOrder, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 完成支付
    @PutMapping("/{id}/pay")
    public ResponseEntity<Order> completePayment(@PathVariable Long id) {
        return orderService.completePayment(id)
                .map(updatedOrder -> new ResponseEntity<>(updatedOrder, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 平台放款
    @PutMapping("/{id}/release")
    public ResponseEntity<Order> releasePayment(@PathVariable Long id) {
        return orderService.releasePayment(id)
                .map(updatedOrder -> new ResponseEntity<>(updatedOrder, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 删除订单（仅管理员可用）
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable Long id,
            @RequestBody Map<String, String> deleteInfo) {
        if (orderService.deleteOrder(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}