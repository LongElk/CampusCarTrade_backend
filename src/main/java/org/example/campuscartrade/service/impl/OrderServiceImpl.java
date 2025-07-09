package org.example.campuscartrade.service.impl;

import org.example.campuscartrade.pojo.Entity.Order;
import org.example.campuscartrade.repository.OrderRepository;
import org.example.campuscartrade.service.OrderService;
import org.example.campuscartrade.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private VehicleService vehicleService;

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("订单不存在"));

        Order.Status currentStatus = order.getStatus();
        Order.Status newStatus = Order.Status.valueOf(status);

        // 合法性校验：
        if (newStatus == Order.Status.SHIPPED && currentStatus != Order.Status.PENDING) {
            throw new RuntimeException("只有待处理订单才能发货");
        }
        if (newStatus == Order.Status.COMPLETED && currentStatus != Order.Status.SHIPPED) {
            throw new RuntimeException("只有已发货订单才能收货");
        }
        if (newStatus == Order.Status.CANCELLED && currentStatus == Order.Status.COMPLETED) {
            throw new RuntimeException("已完成订单不能取消");
        }
        if (newStatus == Order.Status.CANCELLED && currentStatus == Order.Status.SHIPPED) {
            throw new RuntimeException("已发货的订单不能取消");
        }

        order.setStatus(newStatus);
        // 新增：如果订单被取消，将车辆状态改为AVAILABLE
        if (newStatus == Order.Status.CANCELLED) {
            vehicleService.updateStatus(order.getVehicle().getId(), "AVAILABLE");
        }
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
