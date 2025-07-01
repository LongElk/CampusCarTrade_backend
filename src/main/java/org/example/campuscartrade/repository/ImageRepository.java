package org.example.campuscartrade.repository;

import jakarta.transaction.Transactional;
import org.example.campuscartrade.pojo.Entity.Image;
import org.example.campuscartrade.pojo.VO.ImageVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByVehicleId(Long vehicleId);
}
