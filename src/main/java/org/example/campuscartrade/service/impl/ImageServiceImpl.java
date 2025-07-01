package org.example.campuscartrade.service.impl;


import org.example.campuscartrade.Utill.AliOssUtil;
import org.example.campuscartrade.pojo.DTO.ImageDTO;
import org.example.campuscartrade.pojo.Entity.Image;
import org.example.campuscartrade.pojo.VO.ImageVO;
import org.example.campuscartrade.repository.ImageRepository;
import org.example.campuscartrade.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private AliOssUtil aliOssUtil;
    @Override
    public String uploadImage(ImageDTO imageDTO) throws IOException {
        MultipartFile file = imageDTO.getFile();
        int sortOrder = imageDTO.getSortOrder();
        // 保存文件，获取存储的路径
        String filePath = storeFile(file);

        // 保存记录到数据库
        return filePath;
    }
    @Override
    public String storeFile(MultipartFile file) throws IOException {
        //获取文件的原始名 （****.***）
        String originalFilename = file.getOriginalFilename();
        //从最后一个.的索引位置开始截取原始名得到文件名后缀
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        //通过uuid获取随机字符串并与后缀进行拼接 得到上传到阿里云的文件名
        String objectName = UUID.randomUUID() + extension;
        //调用aliossutill工具类的upload方法 获取文件访问路径 返回给前端 前端通过访问该路径进行页面展示
        String path = aliOssUtil.upload(file.getBytes(), objectName);
        return path;
    }

    @Override
    public void save(Long vehicleId, String url, int index) {
        Image image = new Image();
        image.setVehicleId(vehicleId);
        image.setUrl(url);
        image.setSortOrder(index);
        imageRepository.save(image);
    }

    @Override
    public List<Image> getByVehicleId(Long vehicleId) {
        return imageRepository.getByVehicleId(vehicleId);
    }

}
