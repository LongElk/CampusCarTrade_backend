package org.example.campuscartrade.service;

import org.example.campuscartrade.pojo.Entity.Vehicle;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import static org.example.campuscartrade.pojo.Entity.Vehicle.Status.AVAILABLE;
public interface VehicleService {
    Vehicle publish(Vehicle vehicle);
    List<Vehicle> listAvailable(Vehicle.Type type, int page, int size);
    Optional<Vehicle> getById(Long vehicleId);
    Vehicle updateStatus(Long vehicleId, String status);

    List<Vehicle> queryVehicles(Vehicle.Type type, Vehicle.Status status, String keyword, Double minPrice, Double maxPrice, int page, int size);

}