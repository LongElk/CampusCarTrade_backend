package org.example.campuscartrade.pojo.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 后台操作日志表：用于记录管理员在后台的各类操作行为
 * 例如：删除违规信息、审核车辆、生成统计报表等
 */
@Entity
@Table(name = "admin_action_log")
public class AdminActionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 日志唯一ID

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;  // 操作管理员（user表）

    @Column(name = "action_type", nullable = false, length = 50)
    private String actionType;  // 操作类型，例如 DELETE_VEHICLE、BAN_USER 等

    @Column(name = "target_id")
    private Long targetId;  // 操作目标ID（例如车辆ID、用户ID）

    @Column(columnDefinition = "TEXT")
    private String details;  // 操作具体说明

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();  // 操作时间

    // ===== Getter & Setter =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

