package com.placement.filterbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<PlacementDrive> placementDrives = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<CompanyReview> companyReviews = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<CompanyResource> companyResources = new HashSet<>();
}