package com.placement.filterbackend.controller;

import com.placement.filterbackend.entity.Location;
import com.placement.filterbackend.repository.LocationRepository;
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
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    
    @GetMapping
    public List<Location> getAllLocations(@RequestParam(required = false) String search) {
        if (search == null || search.trim().isEmpty()) {
            return locationRepository.findAll();
        } else {
            return locationRepository.findByNameContainingIgnoreCase(search.trim());
        }
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));
        return ResponseEntity.ok(location);
    }

    
    @PostMapping
    public ResponseEntity<?> addLocation(@RequestBody Location location,
                                          @AuthenticationPrincipal AdminDetails adminDetails) {
        if (adminDetails == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        String trimmedName = location.getName().trim();
        location.setName(trimmedName);

        if (locationRepository.existsByNameIgnoreCase(trimmedName)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Location with name '" + trimmedName + "' already exists.");
        }

        try {
            Location saved = locationRepository.save(location);
            return ResponseEntity.ok(saved);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Location already exists.");
        }
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLocation(@PathVariable Long id,
                                            @RequestBody Location location,
                                            @AuthenticationPrincipal AdminDetails adminDetails) {
        if (adminDetails == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        Location existing = locationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));
        existing.setName(location.getName().trim());
        Location updated = locationRepository.save(existing);
        return ResponseEntity.ok(updated);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id,
                                               @AuthenticationPrincipal AdminDetails adminDetails) {
        if (adminDetails == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));
        locationRepository.delete(location);
        return ResponseEntity.noContent().build();
    }
}