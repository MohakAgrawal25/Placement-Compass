package com.placement.filterbackend.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AdminDetails extends User {
    private final Long id;
    private final Long collegeId;

    public AdminDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
                        Long id, Long collegeId) {
        super(username, password, authorities);
        this.id = id;
        this.collegeId = collegeId;
    }

    public boolean isSuperAdmin() {
        return collegeId == null;
    }

    public boolean canAccessCollege(Long targetCollegeId) {
        return isSuperAdmin() || collegeId.equals(targetCollegeId);
    }
}