package org.example.campuscartrade.repository;


import org.example.campuscartrade.pojo.Entity.User;
import org.example.campuscartrade.pojo.Entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 车辆表 Repository：支持标题搜索、价格区间、类型/状态筛选等
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {

    List<Vehicle> findBySeller_Id(Long sellerId);

    List<Vehicle> findByTitleContainingIgnoreCase(String keyword);
    List<Vehicle> findByDescriptionContainingIgnoreCase(String keyword);
    List<Vehicle> findByType(Vehicle.Type type);

    List<Vehicle> findByStatus(Vehicle.Status status);

    List<Vehicle> findByPriceBetween(Double min, Double max);
    List<Vehicle> findByTypeAndStatus(Vehicle.Type type,Vehicle.Status status);


}
