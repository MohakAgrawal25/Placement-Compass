package com.placement.filterbackend.repository;

import com.placement.filterbackend.entity.CompanyReview;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CompanyReviewRepository extends JpaRepository<CompanyReview, Long> {
    List<CompanyReview> findByCollegeId(Long collegeId);
}