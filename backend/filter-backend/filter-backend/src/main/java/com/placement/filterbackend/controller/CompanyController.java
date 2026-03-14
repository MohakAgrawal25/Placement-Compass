package com.placement.filterbackend.controller;

import com.placement.filterbackend.entity.Company;
import com.placement.filterbackend.repository.CompanyRepository;
import com.placement.filterbackend.security.AdminDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    
    @GetMapping
    public List<Company> getAllCompanies(@RequestParam(required = false) String search) {
        if (search == null || search.trim().isEmpty()) {
            return companyRepository.findAll();
        } else {
            return companyRepository.findByNameContainingIgnoreCase(search.trim());
        }
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found"));
        return ResponseEntity.ok(company);
    }

    
    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody Company company,
                                         @AuthenticationPrincipal AdminDetails adminDetails) {
        if (adminDetails == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        String trimmedName = company.getName().trim();
        company.setName(trimmedName);

        if (companyRepository.existsByNameIgnoreCase(trimmedName)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Company with name '" + trimmedName + "' already exists.");
        }

        try {
            Company savedCompany = companyRepository.save(company);
            return ResponseEntity.ok(savedCompany);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Company already exists.");
        }
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable Long id,
                                           @RequestBody Company company,
                                           @AuthenticationPrincipal AdminDetails adminDetails) {
        if (adminDetails == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        Company existing = companyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found"));
        existing.setName(company.getName().trim());
        Company updated = companyRepository.save(existing);
        return ResponseEntity.ok(updated);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id,
                                              @AuthenticationPrincipal AdminDetails adminDetails) {
        if (adminDetails == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found"));
        companyRepository.delete(company);
        return ResponseEntity.noContent().build();
    }
}