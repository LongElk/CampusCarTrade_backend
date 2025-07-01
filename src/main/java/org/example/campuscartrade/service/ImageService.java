package org.example.campuscartrade.service;

import org.example.campuscartrade.pojo.DTO.ImageDTO;
import org.example.campuscartrade.pojo.Entity.Image;
import org.example.campuscartrade.pojo.VO.ImageVO;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public interface ImageService {
    Image uploadImage(ImageDTO imageDTO) throws IOException;

    String storeFile(MultipartFile file) throws IOException;

    void save(Long vehicleId, String url, int index);

    List<ImageVO> getByVehicleId(Long vehicleId);
}
