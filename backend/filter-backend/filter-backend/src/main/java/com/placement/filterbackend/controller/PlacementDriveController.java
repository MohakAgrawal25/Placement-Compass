package com.placement.filterbackend.controller;

import com.placement.filterbackend.dto.*;
import com.placement.filterbackend.entity.*;
import com.placement.filterbackend.repository.*;
import com.placement.filterbackend.security.AdminDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/drives")
public class PlacementDriveController {

    @Autowired
    private PlacementDriveRepository driveRepository;

    @Autowired
    private CollegeRepository collegeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private LocationRepository locationRepository;

    
    @GetMapping
    public List<DriveResponseDTO> getAllDrives(@AuthenticationPrincipal AdminDetails adminDetails) {
        List<PlacementDrive> drives;
        if (adminDetails == null) {
            
            drives = driveRepository.findAll();
        } else if (adminDetails.isSuperAdmin()) {
            drives = driveRepository.findAll();
        } else {
            drives = driveRepository.findByCollegeId(adminDetails.getCollegeId());
        }
        return drives.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<DriveResponseDTO> getDrive(@PathVariable Long id) {
        PlacementDrive drive = driveRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drive not found"));
        return ResponseEntity.ok(convertToDTO(drive));
    }

    
    @PostMapping
    @Transactional
    public ResponseEntity<DriveResponseDTO> createDrive(@RequestBody DriveRequestDTO dto,
                                                        @AuthenticationPrincipal AdminDetails adminDetails) {
        if (adminDetails == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        College college;
        if (adminDetails.isSuperAdmin()) {
            if (dto.getCollegeId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "collegeId is required for super admin");
            }
            college = collegeRepository.findById(dto.getCollegeId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "College not found"));
        } else {
            college = collegeRepository.findById(adminDetails.getCollegeId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "College not found for admin"));
        }

        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company not found"));

        PlacementDrive drive = new PlacementDrive();
        drive.setCollege(college);
        drive.setCompany(company);
        updateDriveFromDTO(drive, dto);

        drive = driveRepository.save(drive);
        return ResponseEntity.ok(convertToDTO(drive));
    }

    
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DriveResponseDTO> updateDrive(@PathVariable Long id,
                                                        @RequestBody DriveRequestDTO dto,
                                                        @AuthenticationPrincipal AdminDetails adminDetails) {
        if (adminDetails == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        PlacementDrive drive = driveRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drive not found"));
        if (!adminDetails.canAccessCollege(drive.getCollege().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        updateDriveFromDTO(drive, dto);
        drive = driveRepository.save(drive);
        return ResponseEntity.ok(convertToDTO(drive));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrive(@PathVariable Long id,
                                            @AuthenticationPrincipal AdminDetails adminDetails) {
        if (adminDetails == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        PlacementDrive drive = driveRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drive not found"));
        if (!adminDetails.canAccessCollege(drive.getCollege().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        driveRepository.delete(drive);
        return ResponseEntity.noContent().build();
    }

    private void updateDriveFromDTO(PlacementDrive drive, DriveRequestDTO dto) {
        
        if (dto.getCompanyId() != null && !dto.getCompanyId().equals(drive.getCompany().getId())) {
            Company company = companyRepository.findById(dto.getCompanyId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company not found"));
            drive.setCompany(company);
        }

        drive.setRole(dto.getRole());
        drive.setMinCgpa(dto.getMinCgpa());
        drive.setMaxAge(dto.getMaxAge());

        if (dto.getGenderPreference() != null) {
            try {
                drive.setGenderPreference(PlacementDrive.GenderPreference.valueOf(dto.getGenderPreference()));
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid gender preference");
            }
        } else {
            drive.setGenderPreference(PlacementDrive.GenderPreference.Any);
        }

        drive.setMaxBacklogs(dto.getMaxBacklogs());
        drive.setRequiredExperienceMonths(dto.getRequiredExperienceMonths() != null ? dto.getRequiredExperienceMonths() : 0);
        drive.setPptDate(dto.getPptDate());
        drive.setPlacementDate(dto.getPlacementDate());
        drive.setYear(dto.getYear());
        drive.setNoOfPeopleHired(dto.getNoOfPeopleHired());
        drive.setPackageMinLpa(dto.getPackageMinLpa());
        drive.setPackageMaxLpa(dto.getPackageMaxLpa());
        drive.setNoOfRounds(dto.getNoOfRounds());
        drive.setEligibilityNotes(dto.getEligibilityNotes());
        drive.setDescription(dto.getDescription());

        if (dto.getBranchIds() != null) {
            Set<Branch> newBranches = new HashSet<>(branchRepository.findAllById(dto.getBranchIds()));
            drive.getBranches().clear();
            drive.getBranches().addAll(newBranches);
        } else {
            drive.getBranches().clear();
        }

        if (dto.getSkillIds() != null) {
            Set<Skill> newSkills = new HashSet<>(skillRepository.findAllById(dto.getSkillIds()));
            drive.getSkills().clear();
            drive.getSkills().addAll(newSkills);
        } else {
            drive.getSkills().clear();
        }

        if (dto.getLocationIds() != null) {
            Set<Location> newLocations = new HashSet<>(locationRepository.findAllById(dto.getLocationIds()));
            drive.getLocations().clear();
            drive.getLocations().addAll(newLocations);
        } else {
            drive.getLocations().clear();
        }
    }

    private DriveResponseDTO convertToDTO(PlacementDrive drive) {
        DriveResponseDTO dto = new DriveResponseDTO();
        dto.setId(drive.getId());
        dto.setCollegeId(drive.getCollege().getId());
        dto.setCollegeName(drive.getCollege().getName());
        dto.setCompanyId(drive.getCompany().getId());
        dto.setCompanyName(drive.getCompany().getName());
        dto.setRole(drive.getRole());
        dto.setMinCgpa(drive.getMinCgpa());
        dto.setMaxAge(drive.getMaxAge());
        dto.setGenderPreference(drive.getGenderPreference() != null ? drive.getGenderPreference().name() : null);
        dto.setMaxBacklogs(drive.getMaxBacklogs());
        dto.setRequiredExperienceMonths(drive.getRequiredExperienceMonths());
        dto.setPptDate(drive.getPptDate());
        dto.setPlacementDate(drive.getPlacementDate());
        dto.setYear(drive.getYear());
        dto.setNoOfPeopleHired(drive.getNoOfPeopleHired());
        dto.setPackageMinLpa(drive.getPackageMinLpa());
        dto.setPackageMaxLpa(drive.getPackageMaxLpa());
        dto.setNoOfRounds(drive.getNoOfRounds());
        dto.setEligibilityNotes(drive.getEligibilityNotes());
        dto.setDescription(drive.getDescription());

        dto.setBranches(drive.getBranches().stream()
                .map(b -> new BranchDTO(b.getId(), b.getName()))
                .collect(Collectors.toList()));
        dto.setSkills(drive.getSkills().stream()
                .map(s -> new SkillDTO(s.getId(), s.getName(), s.getCategory()))
                .collect(Collectors.toList()));
        dto.setLocations(drive.getLocations().stream()
                .map(l -> new LocationDTO(l.getId(), l.getName()))
                .collect(Collectors.toList()));

        return dto;
    }
}