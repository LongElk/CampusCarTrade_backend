package org.example.campuscartrade.pojo.VO;

import org.example.campuscartrade.pojo.Entity.Order;

import java.time.LocalDateTime;

public class OrderVO {
    private Long id;
    private Long buyerId;
    private Long sellerId;
    private Long vehicleId;
    private String vehicleTitle;
    private Double price;
    private LocalDateTime createdTime;
    private LocalDateTime paymentTime;
    private Order.Status status;

    public OrderVO() {
    }

    public OrderVO(Long id, Long buyerId, Long sellerId, Long vehicleId, String vehicleTitle, Double price, LocalDateTime createdTime, LocalDateTime paymentTime, Order.Status status) {
        this.id = id;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.vehicleId = vehicleId;
        this.vehicleTitle = vehicleTitle;
        this.price = price;
        this.createdTime = createdTime;
        this.paymentTime = paymentTime;
        this.status = status;
    }

    public static OrderVO convertToVO(Order order) {
        OrderVO vo = new OrderVO();
        vo.setId(order.getId());
        vo.setBuyerId(order.getBuyer().getId());
        vo.setSellerId(order.getSeller().getId());
        vo.setVehicleId(order.getVehicle().getId());
        vo.setVehicleTitle(order.getVehicle().getTitle());
        vo.setPrice(order.getPrice());
        vo.setCreatedTime(order.getCreatedTime());
        vo.setPaymentTime(order.getPaymentTime());
        vo.setStatus(order.getStatus());
        return vo;
    }

    /**
     * 获取
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取
     * @return buyerId
     */
    public Long getBuyerId() {
        return buyerId;
    }

    /**
     * 设置
     * @param buyerId
     */
    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    /**
     * 获取
     * @return sellerId
     */
    public Long getSellerId() {
        return sellerId;
    }

    /**
     * 设置
     * @param sellerId
     */
    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    /**
     * 获取
     * @return vehicleId
     */
    public Long getVehicleId() {
        return vehicleId;
    }

    /**
     * 设置
     * @param vehicleId
     */
    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    /**
     * 获取
     * @return vehicleTitle
     */
    public String getVehicleTitle() {
        return vehicleTitle;
    }

    /**
     * 设置
     * @param vehicleTitle
     */
    public void setVehicleTitle(String vehicleTitle) {
        this.vehicleTitle = vehicleTitle;
    }

    /**
     * 获取
     * @return price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 设置
     * @param price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * 获取
     * @return createdTime
     */
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    /**
     * 设置
     * @param createdTime
     */
    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 获取
     * @return paymentTime
     */
    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    /**
     * 设置
     * @param paymentTime
     */
    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    /**
     * 获取
     * @return status
     */
    public Order.Status getStatus() {
        return status;
    }

    /**
     * 设置
     * @param status
     */
    public void setStatus(Order.Status status) {
        this.status = status;
    }

    public String toString() {
        return "OrderVO{id = " + id + ", buyerId = " + buyerId + ", sellerId = " + sellerId + ", vehicleId = " + vehicleId + ", vehicleTitle = " + vehicleTitle + ", price = " + price + ", createdTime = " + createdTime + ", paymentTime = " + paymentTime + ", status = " + status + "}";
    }

}