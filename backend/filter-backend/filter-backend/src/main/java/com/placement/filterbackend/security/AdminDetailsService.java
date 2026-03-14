package com.placement.filterbackend.security;

import com.placement.filterbackend.entity.Admin;
import com.placement.filterbackend.repository.AdminRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class AdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    public AdminDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("Admin not found with username: " + username);
        }
        return new AdminDetails(
            admin.getUsername(),
            admin.getPasswordHash(),
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")),
            admin.getId(),
            admin.getCollege() != null ? admin.getCollege().getId() : null
        );
    }
}