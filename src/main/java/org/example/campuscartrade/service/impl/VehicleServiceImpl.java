package org.example.campuscartrade.service.impl;

import jakarta.transaction.Transactional;
import org.example.campuscartrade.pojo.Entity.User;
import org.example.campuscartrade.pojo.Entity.Vehicle;
import org.example.campuscartrade.repository.UserRepository;
import org.example.campuscartrade.repository.VehicleRepository;
import org.example.campuscartrade.service.VehicleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public Optional<Vehicle> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public List<Vehicle> getVehiclesByStatus(Vehicle.Status status) {
        return vehicleRepository.findByStatus(status);
    }

    @Override
    public List<Vehicle> getVehiclesByType(Vehicle.Type type) {
        return vehicleRepository.findByType(type);
    }

    @Override
    public List<Vehicle> getVehiclesBySeller(Long sellerId) {
        return vehicleRepository.findBySellerId(sellerId);
    }

    @Override
    @Transactional
    public Vehicle createVehicle(Vehicle vehicle, Long sellerId) {
        Optional<User> seller = userRepository.findById(sellerId);
        if (seller.isPresent()) {
            vehicle.setSeller(seller.get());
            vehicle.setStatus(Vehicle.Status.AVAILABLE);
            return vehicleRepository.save(vehicle);
        }
        throw new IllegalArgumentException("卖家ID不存在");
    }

    @Override
    @Transactional
    public Optional<Vehicle> updateVehicle(Long id, Vehicle vehicleDetails) {
        return vehicleRepository.findById(id).map(existingVehicle -> {
            existingVehicle.setTitle(vehicleDetails.getTitle());
            existingVehicle.setType(vehicleDetails.getType());
            existingVehicle.setDescription(vehicleDetails.getDescription());
            existingVehicle.setPrice(vehicleDetails.getPrice());
            existingVehicle.setMileage(vehicleDetails.getMileage());
            existingVehicle.setLocation(vehicleDetails.getLocation());
            return vehicleRepository.save(existingVehicle);
        });
    }

    @Override
    @Transactional
    public Optional<Vehicle> updateVehicleStatus(Long id, Vehicle.Status status) {
        return vehicleRepository.findById(id).map(vehicle -> {
            vehicle.setStatus(status);
            return vehicleRepository.save(vehicle);
        });
    }

    @Override
    public boolean deleteVehicle(Long id) {
        if (vehicleRepository.existsById(id)) {
            vehicleRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
