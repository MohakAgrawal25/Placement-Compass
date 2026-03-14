package com.placement.filterbackend.config;

import com.placement.filterbackend.entity.Admin;
import com.placement.filterbackend.entity.College;
import com.placement.filterbackend.repository.AdminRepository;
import com.placement.filterbackend.repository.CollegeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(6)
public class AdminDataLoader implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final CollegeRepository collegeRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminDataLoader(AdminRepository adminRepository, CollegeRepository collegeRepository) {
        this.adminRepository = adminRepository;
        this.collegeRepository = collegeRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {
      
        if (adminRepository.count() == 0) {
            System.out.println("🚀 Inserting default admins...");

            
            Admin superAdmin = Admin.builder()
                    .username("superadmin")
                    .passwordHash(passwordEncoder.encode("admin123"))
                    .college(null)
                    .build();
            adminRepository.save(superAdmin);
            System.out.println("✅ Super admin created: username='superadmin', password='admin123'");

          
            List<College> colleges = collegeRepository.findAll();
            if (colleges.isEmpty()) {
                System.out.println("⚠️ No colleges found – skipping college admin creation.");
            } else {
                int collegeAdminCount = 0;
                for (College college : colleges) {
                    
                    String baseName = college.getName()
                            .toLowerCase()
                            .replaceAll("[^a-z0-9]", "") 
                            .replace(" ", "");
                    String username = "admin_" + baseName;

                    Admin collegeAdmin = Admin.builder()
                            .username(username)
                            .passwordHash(passwordEncoder.encode("college@123"))
                            .college(college)
                            .build();
                    adminRepository.save(collegeAdmin);
                    collegeAdminCount++;
                }
                System.out.println("✅ Created " + collegeAdminCount + " college admins (default password: 'college@123')");
            }
        } else {
            System.out.println("ℹ️ Admins table already contains data. Skipping insertion.");
        }
    }
}