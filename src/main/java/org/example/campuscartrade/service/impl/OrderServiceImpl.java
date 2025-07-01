package org.example.campuscartrade.service.impl;

import org.example.campuscartrade.pojo.Entity.Order;
import org.example.campuscartrade.repository.OrderRepository;
import org.example.campuscartrade.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("订单不存在"));

        // 将传入的字符串转换为 Status 枚举
        Order.Status newStatus = Order.Status.valueOf(status);
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
    @Override
    public void deleteOrder(Long Id){
        orderRepository.deleteById(Id);
    }
    @Override
    public List<Order> getBuyerOrder(Long Id){
        return orderRepository.findByBuyer_Id(Id);
    }
    @Override
    public List<Order> getSellerOrder(Long Id){
        return orderRepository.findBySeller_Id(Id);
    }
    @Override
    public  List<Order> getVehicleOrder(Long Id){
        return orderRepository.findByVehicle_Id(Id);
    }
}
