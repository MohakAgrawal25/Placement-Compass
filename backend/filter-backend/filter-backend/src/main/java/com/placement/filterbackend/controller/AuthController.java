package com.placement.filterbackend.controller;

import com.placement.filterbackend.dto.LoginRequest;
import com.placement.filterbackend.dto.LoginResponse;
import com.placement.filterbackend.entity.Admin;
import com.placement.filterbackend.repository.AdminRepository;
import com.placement.filterbackend.security.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final AdminRepository adminRepository;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtils jwtUtils,
                          AdminRepository adminRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.adminRepository = adminRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Admin admin = adminRepository.findByUsername(request.getUsername());
            if (admin == null) {
                throw new UsernameNotFoundException("Admin not found");
            }

            String token = jwtUtils.generateToken(admin.getUsername(),
                    admin.getCollege() != null ? admin.getCollege().getId() : null);

            return ResponseEntity.ok(new LoginResponse(token, admin.getUsername(),
                    admin.getCollege() != null ? admin.getCollege().getId() : null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}