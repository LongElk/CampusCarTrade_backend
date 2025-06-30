package org.example.campuscartrade.service;
import org.example.campuscartrade.pojo.Entity.VehicleImage;
import java.util.List;
public interface VehicleImageService  {
    VehicleImage uploadImage(VehicleImage image);
    List<VehicleImage> getImagesByVehicleId(Long vehicleId);
}
