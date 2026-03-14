package com.placement.filterbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "placement_drives")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlacementDrive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "college_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private College college;

    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Company company;

    @Column(nullable = false)
    private String role;

    @Column(name = "min_cgpa", precision = 3, scale = 2)
    private BigDecimal minCgpa;

    @Column(name = "max_age")
    private Integer maxAge;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender_preference", columnDefinition = "VARCHAR(10) DEFAULT 'Any'")
    private GenderPreference genderPreference = GenderPreference.Any;

    @Column(name = "max_backlogs")
    private Integer maxBacklogs;

    @Column(name = "required_experience_months")
    private Integer requiredExperienceMonths = 0;

    @Column(name = "ppt_date")
    private LocalDate pptDate;

    @Column(name = "placement_date")
    private LocalDate placementDate;

    @Column(nullable = false)
    private Integer year;

    @Column(name = "no_of_people_hired")
    private Integer noOfPeopleHired;

    @Column(name = "package_min_lpa", precision = 5, scale = 2)
    private BigDecimal packageMinLpa;

    @Column(name = "package_max_lpa", precision = 5, scale = 2)
    private BigDecimal packageMaxLpa;

    @Column(name = "no_of_rounds")
    private Integer noOfRounds;

    @Column(name = "eligibility_notes", length = 1000)
    private String eligibilityNotes;

    @Column(length = 2000)
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
        name = "drive_branches",
        joinColumns = @JoinColumn(name = "drive_id"),
        inverseJoinColumns = @JoinColumn(name = "branch_id")
    )
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<Branch> branches = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "drive_skills",
        joinColumns = @JoinColumn(name = "drive_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<Skill> skills = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "drive_locations",
        joinColumns = @JoinColumn(name = "drive_id"),
        inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<Location> locations = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum GenderPreference {
        M, F, Any
    }
}