package com.placement.filterbackend.controller;

import com.placement.filterbackend.entity.Branch;
import com.placement.filterbackend.repository.BranchRepository;
import com.placement.filterbackend.security.AdminDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping("/api/branches")
public class BranchController {

    @Autowired
    private BranchRepository branchRepository;

    
    @GetMapping
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));
        return ResponseEntity.ok(branch);
    }

    
    @PostMapping
    public ResponseEntity<Branch> createBranch(@RequestBody Branch branch,
                                               @AuthenticationPrincipal AdminDetails adminDetails) {
        if (adminDetails == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        Branch saved = branchRepository.save(branch);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

  
    @PutMapping("/{id}")
    public ResponseEntity<Branch> updateBranch(@PathVariable Long id,
                                               @RequestBody Branch branch,
                                               @AuthenticationPrincipal AdminDetails adminDetails) {
        if (adminDetails == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        Branch existing = branchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));
        existing.setName(branch.getName());
        Branch updated = branchRepository.save(existing);
        return ResponseEntity.ok(updated);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id,
                                             @AuthenticationPrincipal AdminDetails adminDetails) {
        if (adminDetails == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));
        branchRepository.delete(branch);
        return ResponseEntity.noContent().build();
    }
}