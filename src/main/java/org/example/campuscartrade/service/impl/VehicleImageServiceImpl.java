package org.example.campuscartrade.service.impl;

import org.example.campuscartrade.pojo.Entity.VehicleImage;
import org.example.campuscartrade.repository.VehicleImageRepository;
import org.example.campuscartrade.service.VehicleImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleImageServiceImpl implements VehicleImageService {
    @Autowired
    private VehicleImageRepository vehicleImageRepository;

    @Override
    public VehicleImage uploadImage(VehicleImage image) {
        return vehicleImageRepository.save(image);
    }

    @Override
    public List<VehicleImage> getImagesByVehicleId(Long vehicleId) {
        return vehicleImageRepository.findByVehicle_Id(vehicleId);
    }
}
