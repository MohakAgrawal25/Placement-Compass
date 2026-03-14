package com.placement.filterbackend.controller;

import com.placement.filterbackend.dto.BranchSelectionDTO;
import com.placement.filterbackend.entity.Branch;
import com.placement.filterbackend.entity.College;
import com.placement.filterbackend.repository.BranchRepository;
import com.placement.filterbackend.repository.CollegeRepository;
import com.placement.filterbackend.security.AdminDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/colleges")
public class CollegeBranchController {

    @Autowired
    private CollegeRepository collegeRepository;

    @Autowired
    private BranchRepository branchRepository;

    
    @GetMapping("/{collegeId}/branches")
    public Set<Branch> getCollegeBranches(@PathVariable Long collegeId) {
        College college = collegeRepository.findById(collegeId)
                .orElseThrow(() -> new RuntimeException("College not found"));
        return college.getBranches();
    }

    
    @PutMapping("/{collegeId}/branches")
    @Transactional
    public ResponseEntity<?> updateCollegeBranches(@PathVariable Long collegeId,
                                                   @RequestBody BranchSelectionDTO dto,
                                                   @AuthenticationPrincipal AdminDetails adminDetails) {
        if (adminDetails == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        College college = collegeRepository.findById(collegeId)
                .orElseThrow(() -> new RuntimeException("College not found"));
        
        if (!adminDetails.isSuperAdmin() && !adminDetails.getCollegeId().equals(collegeId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }

        List<Branch> selectedBranches = branchRepository.findAllById(dto.getBranchIds());
        college.setBranches(new HashSet<>(selectedBranches));
        collegeRepository.save(college);
        return ResponseEntity.ok().build();
    }
}