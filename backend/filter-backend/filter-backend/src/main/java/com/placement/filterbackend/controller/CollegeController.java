package com.placement.filterbackend.controller;

import com.placement.filterbackend.entity.College;
import com.placement.filterbackend.repository.CollegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.placement.filterbackend.security.AdminDetails;

import java.util.List;

@RestController
@RequestMapping("/api/colleges")
public class CollegeController {

    @Autowired
    private CollegeRepository collegeRepository;

    
    @GetMapping
    public List<College> getAllColleges() {
        return collegeRepository.findAll();
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<College> getCollegeById(@PathVariable Long id) {
        College college = collegeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "College not found"));
        return ResponseEntity.ok(college);
    }

    
    @PostMapping
    public ResponseEntity<College> createCollege(@RequestBody College college,
                                                 @AuthenticationPrincipal AdminDetails adminDetails) {
        
        if (adminDetails == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        College saved = collegeRepository.save(college);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<College> updateCollege(@PathVariable Long id,
                                                 @RequestBody College college,
                                                 @AuthenticationPrincipal AdminDetails adminDetails) {
        if (adminDetails == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        College existing = collegeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "College not found"));
        existing.setName(college.getName());
        existing.setLocation(college.getLocation());
        existing.setWebsite(college.getWebsite());
        
        College updated = collegeRepository.save(existing);
        return ResponseEntity.ok(updated);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollege(@PathVariable Long id,
                                              @AuthenticationPrincipal AdminDetails adminDetails) {
        if (adminDetails == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        College college = collegeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "College not found"));
        collegeRepository.delete(college);
        return ResponseEntity.noContent().build();
    }
}