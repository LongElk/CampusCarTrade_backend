package org.example.campuscartrade;

import org.example.campuscartrade.pojo.Entity.Vehicle;
import org.example.campuscartrade.repository.OrderRepository;
import org.example.campuscartrade.repository.VehicleRepository;
import org.example.campuscartrade.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CampusCarTradeApplicationTests {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Test
    void contextLoads() {
        List<Vehicle> all = vehicleRepository.findAll();
        System.out.println("---------");
        System.out.println(all.get(0).getTitle());
    }

}
