package com.placement.filterbackend.config;

import com.placement.filterbackend.entity.College;
import com.placement.filterbackend.repository.CollegeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Order(4)
public class CollegeDataLoader implements CommandLineRunner {

    private final CollegeRepository collegeRepository;

    public CollegeDataLoader(CollegeRepository collegeRepository) {
        this.collegeRepository = collegeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
       
        if (collegeRepository.count() == 0) {
            List<College> colleges = Arrays.asList(
                createCollege("IIT Bombay", "Mumbai", "https://www.iitb.ac.in"),
                createCollege("IIT Delhi", "New Delhi", "https://www.iitd.ac.in"),
                createCollege("IIT Madras", "Chennai", "https://www.iitm.ac.in"),
                createCollege("IIT Kharagpur", "Kharagpur", "https://www.iitkgp.ac.in"),
                createCollege("IIT Kanpur", "Kanpur", "https://www.iitk.ac.in"),
                createCollege("IIT Roorkee", "Roorkee", "https://www.iitr.ac.in"),
                createCollege("VIT Vellore", "Vellore", "https://www.vit.ac.in"),
                createCollege("BITS Pilani", "Pilani", "https://www.bits-pilani.ac.in"),
                createCollege("NIT Trichy", "Tiruchirappalli", "https://www.nitt.edu"),
                createCollege("NIT Warangal", "Warangal", "https://www.nitw.ac.in"),
                createCollege("Ramdeobaba University", "Nagpur", "https://www.ramdeobaba.edu.in"),
                createCollege("Yeshwantrao Chavan College of Engineering", "Nagpur", "https://www.ycce.edu"),
                createCollege("G H Raisoni College of Engineering", "Nagpur", "https://www.raisoni.net")
                
            );

            collegeRepository.saveAll(colleges);
            System.out.println("✅ Inserted " + colleges.size() + " default colleges.");
        } else {
            System.out.println("ℹ️ Colleges table already contains data. Skipping insertion.");
        }
    }

    private College createCollege(String name, String location, String website) {
        return College.builder()
                .name(name)
                .location(location)
                .website(website)
                .build();
    }
}