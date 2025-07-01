package org.example.campuscartrade.service.impl;

import org.example.campuscartrade.pojo.Entity.Analytics;
import org.example.campuscartrade.pojo.Entity.Vehicle;
import org.example.campuscartrade.repository.AnalyticsRepository;
import org.example.campuscartrade.repository.VehicleRepository;
import org.example.campuscartrade.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    @Autowired
    private AnalyticsRepository analyticsRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public void addView(Long vehicleId) {
        Analytics analytics = analyticsRepository.findByVehicle_Id(vehicleId).orElseGet(() -> {
            Analytics newAnalytics = new Analytics();
            Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow();
            newAnalytics.setVehicle(vehicle);
            return newAnalytics;
        });
        analytics.setViewCount(analytics.getViewCount() + 1);
        analytics.setLastUpdate(LocalDateTime.now());
        analyticsRepository.save(analytics);
    }




    @Override
    public void addFavorite(Long vehicleId) {
        Analytics analytics = analyticsRepository.findByVehicle_Id(vehicleId).orElseGet(() -> {
            Analytics newAnalytics = new Analytics();
            Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(); // 查出车辆实体
            newAnalytics.setVehicle(vehicle); // 设置关联关系
            return newAnalytics;
        });
        analytics.setFavoriteCount(analytics.getFavoriteCount() + 1);
        analytics.setLastUpdate(LocalDateTime.now());
        analyticsRepository.save(analytics);
    }

    @Override
    public void deleteFavorite(Long vehicleId) {
        Analytics analytics = analyticsRepository.findByVehicle_Id(vehicleId).orElseGet(() -> {
            Analytics newAnalytics = new Analytics();
            Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(); // 查出车辆实体
            newAnalytics.setVehicle(vehicle);
            return newAnalytics;
        });
        analytics.setFavoriteCount(Math.max(analytics.getFavoriteCount() - 1, 0)); // 避免负数
        analytics.setLastUpdate(LocalDateTime.now());
        analyticsRepository.save(analytics);
    }


    @Override
    public Analytics getByVehicleId(Long vehicleId) {
        return analyticsRepository.findByVehicle_Id(vehicleId).orElseThrow();
    }

}
