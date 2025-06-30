package org.example.campuscartrade.service;

import org.example.campuscartrade.pojo.Entity.Analytics;

public interface AnalyticsService {
    void addView(Long vehicleId);
    void addFavorite(Long vehicleId);
    void deleteFavorite(Long vehicleId);
    Analytics getByVehicleId(Long vehicleId);
}
