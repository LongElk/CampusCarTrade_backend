package org.example.campuscartrade.pojo.VO;

import org.example.campuscartrade.pojo.Entity.Order;

import java.time.LocalDateTime;

public class OrderVO {
    private Long id;
    private String buyerName;
    private String sellerName;
    private VehiclePage vehicle;
    private Double price;
    private LocalDateTime createdTime;
    private Order.Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public VehiclePage getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehiclePage vehicle) {
        this.vehicle = vehicle;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Order.Status getStatus() {
        return status;
    }

    public void setStatus(Order.Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public OrderVO() {
    }

    public OrderVO(Long id, String buyerName, String sellerName, VehiclePage vehicle, Double price, LocalDateTime createdTime, Order.Status status) {
        this.id = id;
        this.buyerName = buyerName;
        this.sellerName = sellerName;
        this.vehicle = vehicle;
        this.price = price;
        this.createdTime = createdTime;
        this.status = status;
    }
}