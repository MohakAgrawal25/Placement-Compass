package com.placement.filterbackend.repository;

import com.placement.filterbackend.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByNameContainingIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}