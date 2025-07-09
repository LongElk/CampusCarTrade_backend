package org.example.campuscartrade.service.impl;

import org.example.campuscartrade.pojo.Entity.Image;
import org.example.campuscartrade.pojo.Entity.Vehicle;
import org.example.campuscartrade.pojo.VO.ImageVO;
import org.example.campuscartrade.pojo.VO.PageResult;
import org.example.campuscartrade.pojo.VO.VehiclePage;
import org.example.campuscartrade.pojo.VO.VehicleVO;
import org.example.campuscartrade.repository.ImageRepository;
import org.example.campuscartrade.repository.VehicleRepository;
import org.example.campuscartrade.service.VehicleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.campuscartrade.pojo.Entity.Vehicle.Status.AVAILABLE;
import static org.example.campuscartrade.pojo.Entity.Vehicle.Status.SOLD;

@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private ImageRepository imageRepository;

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
    public PageResult<Vehicle> queryVehicles(Vehicle.Type type, Vehicle.Status status, String keyword, Double minPrice, Double maxPrice,int page, int size) {
        List<Vehicle> all = vehicleRepository.findAll(); // 简化版，后续用 Specification 可替代
        List<Vehicle> list = all.stream()
                .filter(v -> type == null || v.getType() == type)
                .filter(v -> v.getStatus() == AVAILABLE) // 保留或修改此行
                // 或者直接替换为：
                // .filter(v -> v.getStatus() == AVAILABLE) // 只保留可用状态
                .filter(v -> keyword == null || v.getTitle().contains(keyword) || v.getDescription().contains(keyword))
                .filter(v -> minPrice == null || v.getPrice() >= minPrice)
                .filter(v -> maxPrice == null || v.getPrice() <= maxPrice)
                .toList();
        int total = list.size();
        List<Vehicle> res = list.stream()
                .skip((long) (page - 1) * size)
                .limit(size)
                .toList();
        return new PageResult<>(total,res);
    }

}
