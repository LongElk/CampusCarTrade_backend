package org.example.campuscartrade.pojo.VO;

import org.example.campuscartrade.pojo.Entity.Vehicle;

import java.time.LocalDateTime;
import java.util.List;

public class VehicleVO {
    private Long id;
    private SellerVO sellerVO;
    private String title;
    private Vehicle.Type type;
    private String description;
    private Double price;
    private Vehicle.Status status;
    private Integer mileage;
    private String location;
    private LocalDateTime publishTime;
    private List<ImageVO> images;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SellerVO getSellerVO() {
        return sellerVO;
    }

    public void setSellerVO(SellerVO sellerVO) {
        this.sellerVO = sellerVO;
    }

    public List<ImageVO> getImages() {
        return images;
    }

    public void setImages(List<ImageVO> images) {
        this.images = images;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Vehicle.Status getStatus() {
        return status;
    }

    public void setStatus(Vehicle.Status status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Vehicle.Type getType() {
        return type;
    }

    public void setType(Vehicle.Type type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public VehicleVO() {
    }

    public VehicleVO(Long id, SellerVO sellerVO, String title, Vehicle.Type type, String description, Double price, Vehicle.Status status, Integer mileage, String location, LocalDateTime publishTime, List<ImageVO> images) {
        this.id = id;
        this.sellerVO = sellerVO;
        this.title = title;
        this.type = type;
        this.description = description;
        this.price = price;
        this.status = status;
        this.mileage = mileage;
        this.location = location;
        this.publishTime = publishTime;
        this.images = images;
    }
}
