package com.placement.filterbackend.controller;

import com.placement.filterbackend.entity.Skill;
import com.placement.filterbackend.repository.SkillRepository;
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
@RequestMapping("/api/skills")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    
    @GetMapping
    public List<Skill> getAllSkills(@RequestParam(required = false) String search) {
        if (search == null || search.trim().isEmpty()) {
            return skillRepository.findAll();
        } else {
            return skillRepository.findByNameContainingIgnoreCase(search.trim());
        }
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Skill not found"));
        return ResponseEntity.ok(skill);
    }

    @PostMapping
    public ResponseEntity<?> addSkill(@RequestBody Skill skill,
                                       @AuthenticationPrincipal AdminDetails adminDetails) {
        if (adminDetails == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        String trimmedName = skill.getName().trim();
        skill.setName(trimmedName);

        if (skillRepository.existsByNameIgnoreCase(trimmedName)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Skill with name '" + trimmedName + "' already exists.");
        }

        try {
            Skill saved = skillRepository.save(skill);
            return ResponseEntity.ok(saved);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Skill already exists.");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateSkill(@PathVariable Long id,
                                         @RequestBody Skill skill,
                                         @AuthenticationPrincipal AdminDetails adminDetails) {
        if (adminDetails == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        Skill existing = skillRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Skill not found"));
        existing.setName(skill.getName().trim());
        existing.setCategory(skill.getCategory());
        Skill updated = skillRepository.save(existing);
        return ResponseEntity.ok(updated);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id,
                                            @AuthenticationPrincipal AdminDetails adminDetails) {
        if (adminDetails == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Skill not found"));
        skillRepository.delete(skill);
        return ResponseEntity.noContent().build();
    }
}