package com.placement.filterbackend.repository;

import com.placement.filterbackend.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollegeRepository extends JpaRepository<College, Long> {
}