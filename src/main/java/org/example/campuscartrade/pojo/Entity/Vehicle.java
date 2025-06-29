package org.example.campuscartrade.pojo.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 车辆表：存储买卖双方发布的二手自行车、电瓶车信息
 */
@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 车辆唯一ID

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;  // 出售人（外键）

    @Column(nullable = false, length = 100)
    private String title;  // 标题

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;  // 类型（自行车 / 电瓶车）

    @Column(columnDefinition = "TEXT")
    private String description;  // 车辆描述

    @Column(nullable = false)
    private Double price;  // 售价

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;  // 状态（在售 /  已售）

    @Column
    private Integer mileage;  // 使用里程（可选）

    @Column(nullable = false)
    private String location;  // 校内地点

    @Column(name = "publish_time", nullable = false)
    private LocalDateTime publishTime = LocalDateTime.now();  // 上架时间

    public enum Type {
        BICYCLE, ELECTRIC
    }

    public enum Status {
        AVAILABLE, SOLD
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }
}

