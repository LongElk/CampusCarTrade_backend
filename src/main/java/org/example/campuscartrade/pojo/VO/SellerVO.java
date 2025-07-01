package org.example.campuscartrade.pojo.VO;

import org.apache.commons.codec.language.bm.Rule;

public class SellerVO {
    private Long id;
    private String name;
    private String phone;

    public SellerVO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public SellerVO(Long id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }
}
