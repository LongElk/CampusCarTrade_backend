package org.example.campuscartrade.pojo.Entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "vehicle_image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long vehicleId;

    @Column(nullable = false)
    private String url;

    private int sortOrder;

    public Image() {
    }

    public Image(Long id, Long vehicleId, String url, int sortOrder) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.url = url;
        this.sortOrder = sortOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
