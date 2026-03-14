package com.placement.filterbackend.repository;

import com.placement.filterbackend.entity.CompanyResource;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CompanyResourceRepository extends JpaRepository<CompanyResource, Long> {
    List<CompanyResource> findByCollegeId(Long collegeId);
}