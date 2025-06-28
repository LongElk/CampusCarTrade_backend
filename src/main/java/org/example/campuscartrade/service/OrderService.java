package org.example.campuscartrade.service;

import org.example.campuscartrade.pojo.Entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    // 获取所有订单
    List<Order> getAllOrders();

    // 根据ID获取订单
    Optional<Order> getOrderById(Long id);

    // 根据买家ID获取订单
    List<Order> getOrdersByBuyer(Long buyerId);

    // 根据卖家ID获取订单
    List<Order> getOrdersBySeller(Long sellerId);

    // 根据状态获取订单
    List<Order> getOrdersByStatus(Order.Status status);

    // 创建订单
    Order createOrder(Order order, Long buyerId, Long vehicleId);

    // 更新订单状态
    Optional<Order> updateOrderStatus(Long id, Order.Status status);

    // 完成支付
    Optional<Order> completePayment(Long id);

    // 平台放款
    Optional<Order> releasePayment(Long id);

    // 删除订单
    boolean deleteOrder(Long id);
}
