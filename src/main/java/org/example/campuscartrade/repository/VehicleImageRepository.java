package org.example.campuscartrade.repository;


import org.example.campuscartrade.pojo.Entity.Vehicle;
import org.example.campuscartrade.pojo.Entity.VehicleImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 车辆图片表 Repository：按车辆ID查询配图
 */
@Repository
public interface VehicleImageRepository extends JpaRepository<VehicleImage, Long> {

    List<VehicleImage> findByVehicle(Vehicle vehicle);

    List<VehicleImage> findByVehicleIdOrderBySortOrderAsc(Long vehicleId);
}
