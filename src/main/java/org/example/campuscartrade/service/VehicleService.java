package org.example.campuscartrade.service;

import org.example.campuscartrade.pojo.Entity.Vehicle;
import org.example.campuscartrade.pojo.VO.VehiclePage;
import org.example.campuscartrade.pojo.VO.VehicleVO;

import java.util.List;
import java.util.Optional;

public interface VehicleService {
    Vehicle publish(Vehicle vehicle);
    List<Vehicle> listAvailable(Vehicle.Type type, int page, int size);
    Optional<Vehicle> getById(Long vehicleId);
    Vehicle updateStatus(Long vehicleId, String status);

    List<Vehicle> queryVehicles(Vehicle.Type type, Vehicle.Status status, String keyword, Double minPrice, Double maxPrice, int page, int size);

}