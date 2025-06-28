package org.example.campuscartrade.pojo.Entity;

import jakarta.persistence.*;

/**
 * 车辆图片表：存储车辆配图（最多4张），用于车辆详情页展示
 */
@Entity
@Table(name = "vehicle_image")
public class VehicleImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 图片ID

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;  // 所属车辆

    @Column(nullable = false, length = 255)
    private String url;  // 图片存储路径（OSS）

    @Column(name = "sort_order")
    private Integer sortOrder;  // 排序顺序（1~4）
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}

