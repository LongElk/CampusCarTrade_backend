package org.example.campuscartrade.controller;

import org.example.campuscartrade.pojo.Entity.Vehicle;
import org.example.campuscartrade.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin(origins = "*")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    // 获取所有车辆
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    // 根据ID获取车辆
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        return vehicleService.getVehicleById(id)
                .map(vehicle -> new ResponseEntity<>(vehicle, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 根据状态获取车辆
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Vehicle>> getVehiclesByStatus(@PathVariable Vehicle.Status status) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByStatus(status);
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    // 根据类型获取车辆
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Vehicle>> getVehiclesByType(@PathVariable Vehicle.Type type) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByType(type);
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    // 根据卖家ID获取车辆
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<Vehicle>> getVehiclesBySeller(@PathVariable Long sellerId) {
        List<Vehicle> vehicles = vehicleService.getVehiclesBySeller(sellerId);
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    // 创建车辆
    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle, @RequestParam Long sellerId) {
        Vehicle createdVehicle = vehicleService.createVehicle(vehicle, sellerId);
        return new ResponseEntity<>(createdVehicle, HttpStatus.CREATED);
    }

    // 更新车辆
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @RequestBody Vehicle vehicleDetails) {
        return vehicleService.updateVehicle(id, vehicleDetails)
                .map(updatedVehicle -> new ResponseEntity<>(updatedVehicle, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 更新车辆状态
    @PutMapping("/{id}/status")
    public ResponseEntity<Vehicle> updateVehicleStatus(@PathVariable Long id, @RequestParam Vehicle.Status status) {
        return vehicleService.updateVehicleStatus(id, status)
                .map(updatedVehicle -> new ResponseEntity<>(updatedVehicle, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 删除车辆
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        if (vehicleService.deleteVehicle(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
