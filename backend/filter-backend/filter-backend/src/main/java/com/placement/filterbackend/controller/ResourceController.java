package com.placement.filterbackend.controller;

import com.placement.filterbackend.entity.CompanyResource;
import com.placement.filterbackend.repository.CompanyResourceRepository;
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
@RequestMapping("/api/resources")
public class ResourceController {

    @Autowired
    private CompanyResourceRepository resourceRepository;

    @Autowired
    private CollegeRepository collegeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping
    public List<CompanyResource> getAllResources() {
        return resourceRepository.findAll();
    }

    
    @GetMapping("/college/{collegeId}")
    public List<CompanyResource> getResourcesByCollege(@PathVariable Long collegeId) {
        return resourceRepository.findByCollegeId(collegeId);
    }

    
    @PostMapping
    public ResponseEntity<CompanyResource> createResource(@RequestBody CompanyResource resource) {
        if (!collegeRepository.existsById(resource.getCollege().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "College not found");
        }
        if (!companyRepository.existsById(resource.getCompany().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company not found");
        }
        CompanyResource saved = resourceRepository.save(resource);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id,
                                               @AuthenticationPrincipal AdminDetails adminDetails) {
        if (adminDetails == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        CompanyResource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));

        
        if (!adminDetails.isSuperAdmin()) {
            
            Long collegeId = resource.getCollege().getId();
            if (!adminDetails.getCollegeId().equals(collegeId)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied: not your college");
            }
        }

        resourceRepository.delete(resource);
        return ResponseEntity.noContent().build();
    }
}