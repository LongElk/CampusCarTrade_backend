package org.example.campuscartrade.service;

import org.example.campuscartrade.pojo.Entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(Order order);
    /**
     * 更新订单状态（支持发货、收货、取消）
     * @param orderId 订单ID
     * @param status 新状态（SHIPPED/COMPLETED/CANCELLED）
     */
    Order updateOrderStatus(Long orderId, String status);
    void deleteOrder(Long Id);
    List<Order> getBuyerOrder(Long Id);
    List<Order> getSellerOrder(Long Id);
    List<Order> getVehicleOrder(Long Id);
}
