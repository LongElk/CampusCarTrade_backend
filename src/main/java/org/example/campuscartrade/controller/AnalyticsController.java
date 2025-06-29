package org.example.campuscartrade.controller;

import org.example.campuscartrade.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    // 4.1 增加浏览次数
    @PostMapping("/{vehicleId}/view")
    public ResponseEntity<Map<String, Object>> addView(@PathVariable Long vehicleId) {
        analyticsService.addView(vehicleId);
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("message", "浏览次数增加成功");
        return ResponseEntity.ok(res);
    }

    // 4.2 收藏/取消收藏
    @PostMapping("/{vehicleId}/favorite")
    public ResponseEntity<Map<String, Object>> favorite(@PathVariable Long vehicleId,
                                                        @RequestBody Map<String, String> body) {
        String action = body.get("action");
        Map<String, Object> res = new HashMap<>();

        if ("add".equalsIgnoreCase(action)) {
            analyticsService.addFavorite(vehicleId);
            res.put("code", 200);
            res.put("message", "收藏成功");
        } else if ("remove".equalsIgnoreCase(action)) {
            analyticsService.deleteFavorite(vehicleId);
            res.put("code", 200);
            res.put("message", "取消收藏成功");
        } else {
            res.put("code", 400);
            res.put("message", "无效的操作");
            return ResponseEntity.badRequest().body(res);
        }

        return ResponseEntity.ok(res);
    }
}

