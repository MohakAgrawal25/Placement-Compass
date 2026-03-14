package com.placement.filterbackend.controller;

import com.placement.filterbackend.entity.CompanyReview;
import com.placement.filterbackend.repository.CompanyReviewRepository;
import com.placement.filterbackend.repository.CollegeRepository;
import com.placement.filterbackend.repository.CompanyRepository;
import com.placement.filterbackend.security.AdminDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private CompanyReviewRepository reviewRepository;

    @Autowired
    private CollegeRepository collegeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    
    @GetMapping
    public List<CompanyReview> getAllReviews() {
        return reviewRepository.findAll();
    }

    
    @GetMapping("/college/{collegeId}")
    public List<CompanyReview> getReviewsByCollege(@PathVariable Long collegeId) {
        return reviewRepository.findByCollegeId(collegeId);
    }

    
    @PostMapping
    public ResponseEntity<CompanyReview> createReview(@RequestBody CompanyReview review) {
        if (!collegeRepository.existsById(review.getCollege().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "College not found");
        }
        if (!companyRepository.existsById(review.getCompany().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company not found");
        }
        CompanyReview saved = reviewRepository.save(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id,
                                             @AuthenticationPrincipal AdminDetails adminDetails) {
        if (adminDetails == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        CompanyReview review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));

        
        if (!adminDetails.isSuperAdmin()) {
            
            Long collegeId = review.getCollege().getId();
            if (!adminDetails.getCollegeId().equals(collegeId)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied: not your college");
            }
        }

        reviewRepository.delete(review);
        return ResponseEntity.noContent().build();
    }
}