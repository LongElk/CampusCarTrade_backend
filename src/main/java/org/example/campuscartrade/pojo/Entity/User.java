package org.example.campuscartrade.pojo.Entity;

import jakarta.persistence.*;

/**
 * 用户表：用于存储平台用户（买家、卖家、管理员）的基本信息
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 用户唯一ID

    @Column(nullable = false, length = 50)
    private String name;  // 用户姓名

    @Column(nullable = false, unique = true, length = 100)
    private String email;  // 校园邮箱，唯一

    @Column(length = 20)
    private String phone;  // 联系电话

    @Column(nullable = false)
    private String passwordHash;  // 加密后的密码

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;  // 用户角色（买家/卖家/管理员）

    @Column(nullable = false, length = 100)
    private String school;  // 所属学校

    public enum Role {
        USER, ADMIN
    }

    // Getters and Setters

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}

