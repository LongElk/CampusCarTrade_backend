package org.example.campuscartrade.repository;


import org.example.campuscartrade.pojo.Entity.Order;
import org.example.campuscartrade.pojo.Entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单表 Repository：按买家/卖家/状态筛选订单
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByVehicle_Id(Long vehicle);
    List<Order> findByBuyer_Id(Long buyerId);
    List<Order> findBySeller_Id(Long sellerId);
    void deleteById(Long Id);

}
