package com.placement.filterbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "skills")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String category;   

    @ManyToMany(mappedBy = "skills")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<PlacementDrive> placementDrives = new HashSet<>();
}