package org.example.campuscartrade.pojo.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 分析统计表：记录每辆车的浏览量和收藏量等数据
 */
@Entity
@Table(name = "analytics")
public class Analytics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 分析记录ID

    @OneToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;  // 对应车辆

    private Integer viewCount = 0;  // 浏览次数

    private Integer favoriteCount = 0;  // 收藏次数

    @Column(name = "last_update")
    private LocalDateTime lastUpdate = LocalDateTime.now();  // 最后更新时间

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

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
