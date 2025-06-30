package org.example.campuscartrade.service.impl;

import org.example.campuscartrade.pojo.Entity.Analytics;
import org.example.campuscartrade.repository.AnalyticsRepository;
import org.example.campuscartrade.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    @Autowired
    private AnalyticsRepository analyticsRepository;

    @Override
    public void addView(Long vehicleId) {
        Analytics analytics = analyticsRepository.findByVehicle_Id(vehicleId).orElseGet(() -> {
            Analytics newAnalytics = new Analytics();
            newAnalytics.setVehicleId(vehicleId);
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
            newAnalytics.setVehicleId(vehicleId);
            return newAnalytics;
        });
        analytics.setFavoriteCount(analytics.getFavoriteCount() + 1);
        analytics.setLastUpdate(LocalDateTime.now());
        analyticsRepository.save(analytics);
    }

    @Override
    public Analytics getByVehicleId(Long vehicleId) {
        return analyticsRepository.findByVehicle_Id(vehicleId).orElseThrow();
    }

    @Override
    public void deleteFavorite(Long vehicleId){
        Analytics analytics = analyticsRepository.findByVehicle_Id(vehicleId).orElseGet(() -> {
            Analytics newAnalytics = new Analytics();
            newAnalytics.setVehicleId(vehicleId);
            return newAnalytics;
        });
        analytics.setFavoriteCount(analytics.getFavoriteCount() - 1);
        analytics.setLastUpdate(LocalDateTime.now());
        analyticsRepository.save(analytics);
    }
}
