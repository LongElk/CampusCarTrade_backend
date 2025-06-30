package org.example.campuscartrade.repository;


import org.example.campuscartrade.pojo.Entity.AdminActionLog;
import org.example.campuscartrade.pojo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 管理员操作日志 Repository：支持按管理员或操作类型查看日志
 */
@Repository
public interface AdminActionLogRepository extends JpaRepository<AdminActionLog, Long> {

    List<AdminActionLog> findByAdmin_Id(Long adminId);

    List<AdminActionLog> findByActionType(String actionType);
}
