package org.example.campuscartrade.service.impl;

import org.example.campuscartrade.pojo.Entity.Image;
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
    public Image uploadImage(Image image) {
        return vehicleImageRepository.save(image);
    }

    @Override
    public List<Image> getImagesByVehicleId(Long vehicleId) {
        return vehicleImageRepository.findByVehicleId(vehicleId);
    }
}
