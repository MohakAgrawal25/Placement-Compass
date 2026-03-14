package com.placement.filterbackend.repository;

import com.placement.filterbackend.entity.PlacementDrive;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlacementDriveRepository extends JpaRepository<PlacementDrive, Long> {
    List<PlacementDrive> findByCollegeId(Long collegeId);
}