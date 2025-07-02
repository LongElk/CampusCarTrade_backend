package org.example.campuscartrade.pojo.VO;

public class VehiclePage {
    private String title;
    private Double price;
    private String imageUrl;
    private Long id;

    public VehiclePage(String title, Double price, String imageUrl, Long id) {
        this.title = title;
        this.price = price;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehiclePage() {
    }
}
