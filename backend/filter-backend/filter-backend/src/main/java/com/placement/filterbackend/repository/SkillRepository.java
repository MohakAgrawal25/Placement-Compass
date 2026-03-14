package com.placement.filterbackend.repository;

import com.placement.filterbackend.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByNameContainingIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}