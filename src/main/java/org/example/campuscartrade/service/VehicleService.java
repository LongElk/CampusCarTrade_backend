package org.example.campuscartrade.service;

import org.example.campuscartrade.pojo.Entity.Vehicle;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface VehicleService {
    // 获取所有车辆
    List<Vehicle> getAllVehicles();

    // 根据ID获取车辆
    Optional<Vehicle> getVehicleById(Long id);

    // 根据状态获取车辆
    List<Vehicle> getVehiclesByStatus(Vehicle.Status status);

    // 根据类型获取车辆
    List<Vehicle> getVehiclesByType(Vehicle.Type type);

    // 根据卖家ID获取车辆
    List<Vehicle> getVehiclesBySeller(Long sellerId);

    // 创建车辆
    Vehicle createVehicle(Vehicle vehicle, Long sellerId);

    // 更新车辆
    Optional<Vehicle> updateVehicle(Long id, Vehicle vehicleDetails);

    // 更新车辆状态
    Optional<Vehicle> updateVehicleStatus(Long id, Vehicle.Status status);

    // 删除车辆
    boolean deleteVehicle(Long id);

}
