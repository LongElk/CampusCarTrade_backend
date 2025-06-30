package org.example.campuscartrade.service;
import org.example.campuscartrade.pojo.Entity.Image;
import org.example.campuscartrade.pojo.Entity.Vehicle;

import java.util.List;
public interface VehicleImageService  {
    Image uploadImage(Image image);
    List<Image> getImagesByVehicleId(Long vehicleId);
}
