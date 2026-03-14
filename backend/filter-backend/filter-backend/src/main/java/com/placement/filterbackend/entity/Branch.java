package com.placement.filterbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "branches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "branches")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<College> colleges = new HashSet<>();

    @ManyToMany(mappedBy = "branches")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<PlacementDrive> placementDrives = new HashSet<>();
}