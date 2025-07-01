package org.example.campuscartrade.controller;

import org.example.campuscartrade.pojo.DTO.ImageDTO;
import org.example.campuscartrade.pojo.Entity.Image;
import org.example.campuscartrade.pojo.VO.ImageVO;
import org.example.campuscartrade.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Autowired
    private ImageService imageService;
    /**
     * 上传图片接口，同时保存图片文件和图片信息
     *
     * @param imageDTO 封装了上传文件及其他参数的 DTO
     * @return 上传成功返回图片信息，否则返回500错误
     */
    @PostMapping("/upload")
    public ResponseEntity<ImageVO> uploadImage(@ModelAttribute ImageDTO imageDTO) throws IOException {

        org.example.campuscartrade.pojo.Entity.Image savedImage = imageService.uploadImage(imageDTO);
        if (savedImage != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ImageVO(savedImage.getId(),savedImage.getUrl(),savedImage.getSortOrder()));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
