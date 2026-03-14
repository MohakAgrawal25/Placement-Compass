package com.placement.filterbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "colleges")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String location;
    private String website;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
        name = "college_branches",
        joinColumns = @JoinColumn(name = "college_id"),
        inverseJoinColumns = @JoinColumn(name = "branch_id")
    )
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<Branch> branches = new HashSet<>();

    @OneToMany(mappedBy = "college")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<PlacementDrive> placementDrives = new HashSet<>();

    @OneToMany(mappedBy = "college")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<CompanyReview> companyReviews = new HashSet<>();

    @OneToMany(mappedBy = "college")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<CompanyResource> companyResources = new HashSet<>();

    @OneToMany(mappedBy = "college")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<Admin> admins = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}