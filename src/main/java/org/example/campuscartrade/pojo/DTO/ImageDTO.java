package org.example.campuscartrade.pojo.DTO;

import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

public class ImageDTO {
    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public ImageDTO() {
    }

    public ImageDTO(MultipartFile file, int sortOrder) {
        this.file = file;
        this.sortOrder = sortOrder;
    }

    // 用于接收上传的文件
    private MultipartFile file;


    // 显示顺序，默认0
    private int sortOrder = 0;

}