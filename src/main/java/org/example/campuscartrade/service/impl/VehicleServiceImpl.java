package org.example.campuscartrade.service.impl;

import org.example.campuscartrade.pojo.Entity.Vehicle;
import org.example.campuscartrade.pojo.VO.ImageVO;
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
    public List<VehicleVO> queryVehicles(Vehicle.Type type, Vehicle.Status status, String keyword, int page, int size) {
        List<Vehicle> all = vehicleRepository.findAll(); // 简化版，后续用 Specification 可替代
        List<VehicleVO> vehicleVOS = new ArrayList<>(all.size());
        BeanUtils.copyProperties(all,vehicleVOS);
        for (VehicleVO vehicleVO : vehicleVOS) {
            List<ImageVO> imageVOS = imageRepository.getByVehicleId(vehicleVO.getId());
            vehicleVO.setImages(imageVOS);
        }
        return vehicleVOS.stream()
                .filter(v -> type == null || v.getType() == type)
                .filter(v -> status == null || v.getStatus() == status)
                .filter(v -> keyword == null || v.getTitle().contains(keyword)  || v.getDescription().contains(keyword))
                .skip((long) (page - 1) * size)
                .limit(size)
                .toList();
    }

}
