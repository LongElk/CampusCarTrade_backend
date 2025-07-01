package org.example.campuscartrade.service.impl;

import org.example.campuscartrade.pojo.Entity.Vehicle;
import org.example.campuscartrade.repository.VehicleRepository;
import org.example.campuscartrade.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.example.campuscartrade.pojo.Entity.Vehicle.Status.AVAILABLE;

@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public Vehicle publish(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public List<Vehicle> listAvailable(Vehicle.Type type, int page, int size) {

        // 分页略去简化实现
        return vehicleRepository.findByTypeAndStatus(type, AVAILABLE);
    }

    @Override
    public Optional<Vehicle> getById(Long vehicleId) {
        return vehicleRepository.findById(vehicleId);
    }

    @Override
    public Vehicle updateStatus(Long vehicleId, String status) {
        Vehicle v = vehicleRepository.findById(vehicleId).orElseThrow();

        return vehicleRepository.save(v);
    }
    @Override
    public List<Vehicle> queryVehicles(Vehicle.Type type, Vehicle.Status status, String keyword, Double minPrice, Double maxPrice, int page, int size) {
        List<Vehicle> all = vehicleRepository.findAll();
        return all.stream()
                .filter(v -> type == null || v.getType() == type)
                .filter(v -> status == null || v.getStatus() == status)
                .filter(v -> keyword == null || v.getTitle().contains(keyword) || v.getDescription().contains(keyword))
                .filter(v -> minPrice == null || v.getPrice() >= minPrice)
                .filter(v -> maxPrice == null || v.getPrice() <= maxPrice)
                .skip((long) (page - 1) * size)
                .limit(size)
                .toList();
    }
}
