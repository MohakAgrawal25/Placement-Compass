package com.placement.filterbackend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class DriveRequestDTO {
    private Long collegeId; 
    private Long companyId;
    private String role;
    private BigDecimal minCgpa;
    private Integer maxAge;
    private String genderPreference; 
    private Integer maxBacklogs;
    private Integer requiredExperienceMonths;
    private LocalDate pptDate;
    private LocalDate placementDate;
    private Integer year;
    private Integer noOfPeopleHired;
    private BigDecimal packageMinLpa;
    private BigDecimal packageMaxLpa;
    private Integer noOfRounds;
    private String eligibilityNotes;
    private String description;
    private List<Long> branchIds;
    private List<Long> skillIds;
    private List<Long> locationIds;
}