package org.example.campuscartrade.pojo.DTO;

import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

public class ImageDTO {
    public ImageDTO(MultipartFile file, Long vehicleId, int sortOrder) {
        this.file = file;
        this.vehicleId = vehicleId;
        this.sortOrder = sortOrder;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    // 用于接收上传的文件
    private MultipartFile file;

    // 车辆ID
    private Long vehicleId;

    // 显示顺序，默认0
    private int sortOrder = 0;

}