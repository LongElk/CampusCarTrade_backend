package org.example.campuscartrade.repository;

import org.example.campuscartrade.pojo.Entity.Analytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 分析表 Repository：按车辆ID查询分析数据
 */
@Repository
public interface AnalyticsRepository extends JpaRepository<Analytics, Long> {

    boolean existsByVehicle_Id(Long vehicle);//未使用

    Optional<Analytics> findByVehicle_Id(Long vehicleId);
}
