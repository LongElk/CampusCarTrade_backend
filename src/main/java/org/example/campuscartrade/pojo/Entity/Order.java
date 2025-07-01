package org.example.campuscartrade.pojo.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 订单表：记录买卖双方交易信息及支付流程
 */
@Entity
@Table(name = "orders") // 避免使用SQL关键字
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 订单ID

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;  // 交易车辆

    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;  // 买家

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;  // 卖家

    @Column(nullable = false)
    private Double price;  // 成交价格

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime = LocalDateTime.now();  // 下单时间

    @Column(name = "payment_time")
    private LocalDateTime paymentTime;  // 买家支付时间

    @Column(nullable = false)
    private Status status = Status.PENDING;  // 默认“待处理”

    public enum Status {
        PENDING,   // 待卖家确认
        CONFIRMED, // 卖家同意
        CANCELLED  // 取消（买家或卖家）
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}

