package org.example.campuscartrade.service.impl;

import jakarta.transaction.Transactional;
import org.example.campuscartrade.pojo.Entity.Order;
import org.example.campuscartrade.pojo.Entity.User;
import org.example.campuscartrade.pojo.Entity.Vehicle;
import org.example.campuscartrade.repository.OrderRepository;
import org.example.campuscartrade.repository.UserRepository;
import org.example.campuscartrade.repository.VehicleRepository;
import org.example.campuscartrade.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            VehicleRepository vehicleRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Map<String, Object> getOrders(int page, int size, String role, Order.Status status) {
        return Map.of();
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getOrdersByBuyer(Long buyerId) {
        return orderRepository.findByBuyerId(buyerId);
    }

    @Override
    public List<Order> getOrdersBySeller(Long sellerId) {
        return orderRepository.findBySellerId(sellerId);
    }

    @Override
    public List<Order> getOrdersByStatus(Order.Status status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    @Transactional
    public Order createOrder(Order order, Long buyerId, Long vehicleId) {
        // 验证买家和车辆是否存在
        Optional<User> buyer = userRepository.findById(buyerId);
        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleId);

        if (buyer.isPresent() && vehicle.isPresent()) {
            // 验证车辆状态是否为在售
            if (vehicle.get().getStatus() == Vehicle.Status.AVAILABLE) {
                order.setBuyer(buyer.get());
                order.setSeller(vehicle.get().getSeller());
                order.setVehicle(vehicle.get());
                order.setPrice(vehicle.get().getPrice());
                order.setStatus(Order.Status.PENDING);
                order.setCreatedTime(LocalDateTime.now());

                // 更新车辆状态为预定
                vehicle.get().setStatus(Vehicle.Status.RESERVED);
                vehicleRepository.save(vehicle.get());

                return orderRepository.save(order);
            } else {
                throw new IllegalArgumentException("车辆状态不是在售，无法创建订单");
            }
        } else {
            throw new IllegalArgumentException("买家或车辆不存在");
        }
    }

    @Override
    @Transactional
    public Optional<Order> updateOrderStatus(Long id, Order.Status status) {
        return orderRepository.findById(id).map(existingOrder -> {
            existingOrder.setStatus(status);
            return orderRepository.save(existingOrder);
        });
    }

    @Override
    @Transactional
    public Optional<Order> completePayment(Long id) {
        return orderRepository.findById(id).map(order -> {
            if (order.getStatus() == Order.Status.PENDING) {
                order.setStatus(Order.Status.PAID);
                order.setPaymentTime(LocalDateTime.now());
                return orderRepository.save(order);
            }
            return order;
        });
    }

    @Override
    @Transactional
    public Optional<Order> releasePayment(Long id) {
        return orderRepository.findById(id).map(order -> {
            if (order.getStatus() == Order.Status.PAID) {
                order.setStatus(Order.Status.COMPLETED);
                order.setReleaseTime(LocalDateTime.now());

                // 更新车辆状态为已售
                Vehicle vehicle = order.getVehicle();
                vehicle.setStatus(Vehicle.Status.SOLD);
                vehicleRepository.save(vehicle);

                return orderRepository.save(order);
            }
            return order;
        });
    }

    @Override
    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}