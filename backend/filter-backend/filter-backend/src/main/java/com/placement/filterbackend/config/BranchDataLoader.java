package com.placement.filterbackend.config;

import com.placement.filterbackend.entity.Branch;
import com.placement.filterbackend.repository.BranchRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Order(1)
public class BranchDataLoader implements CommandLineRunner {

    private final BranchRepository branchRepository;

    public BranchDataLoader(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public void run(String... args) throws Exception {
     
        if (branchRepository.count() == 0) {
            List<Branch> branches = Arrays.asList(
                createBranch("Computer Science and Engineering"),
                createBranch("Electronics and Communication Engineering"),
                createBranch("Electrical and Electronics Engineering"),
                createBranch("Mechanical Engineering"),
                createBranch("Civil Engineering"),
                createBranch("Chemical Engineering"),
                createBranch("Information Technology"),
                createBranch("Aerospace Engineering"),
                createBranch("Biotechnology"),
                createBranch("Instrumentation and Control Engineering"),
                createBranch("Production Engineering"),
                createBranch("Metallurgical Engineering"),
                createBranch("Mining Engineering"),
                createBranch("Petroleum Engineering"),
                createBranch("Textile Engineering"),
                createBranch("Environmental Engineering"),
                createBranch("Marine Engineering"),
                createBranch("Automobile Engineering"),
                createBranch("Robotics and Automation"),
                createBranch("Artificial Intelligence and Machine Learning"),
                createBranch("Data Science"),
                createBranch("Cyber Security"),
                createBranch("IoT and Blockchain"),
                
                createBranch("Biomedical Engineering"),
                createBranch("Mechatronics Engineering"),
                createBranch("Nanotechnology"),
                createBranch("Nuclear Engineering"),
                createBranch("Ocean Engineering"),
                createBranch("Polymer Engineering"),
                createBranch("Industrial Engineering"),
                createBranch("Manufacturing Engineering"),
                createBranch("Software Engineering"),
                createBranch("Computer Engineering"),
                createBranch("Information Science"),
                createBranch("Electronics and Telecommunication Engineering"),
                createBranch("Power Engineering"),
                createBranch("Control Systems Engineering"),
                createBranch("Energy Engineering"),
                createBranch("Engineering Physics"),
                createBranch("Engineering Chemistry"),
                createBranch("Applied Mathematics"),
                createBranch("Agricultural Engineering"),
                createBranch("Food Technology"),
                createBranch("Dairy Technology"),
                createBranch("Leather Technology"),
                createBranch("Printing Technology"),
                createBranch("Plastic Engineering"),
                createBranch("Ceramic Engineering"),
                createBranch("Mining Machinery Engineering"),
                createBranch("Construction Engineering"),
                createBranch("Structural Engineering"),
                createBranch("Transportation Engineering"),
                createBranch("Geotechnical Engineering"),
                createBranch("Water Resources Engineering"),
                createBranch("Remote Sensing and GIS"),
                createBranch("Geoinformatics"),
                createBranch("Biochemical Engineering"),
                createBranch("Genetic Engineering"),
                createBranch("Pharmaceutical Engineering"),
                createBranch("Optical Engineering"),
                createBranch("Aeronautical Engineering"),
                createBranch("Astronautical Engineering"),
                createBranch("Avionics"),
                createBranch("Automation Engineering"),
                createBranch("Embedded Systems"),
                createBranch("VLSI Design"),
                createBranch("Microelectronics"),
                createBranch("Telecommunication Engineering"),
                createBranch("Radio Engineering"),
                createBranch("Satellite Engineering"),
                createBranch("Naval Architecture"),
                createBranch("Offshore Engineering"),
                createBranch("Port and Harbour Engineering"),
                createBranch("Harbour Engineering"),
                createBranch("Fire Engineering"),
                createBranch("Safety Engineering"),
                createBranch("Industrial Safety Engineering"),
                createBranch("Mechatronics and Automation"),
                createBranch("Robotics Engineering"),
                createBranch("Cybernetics"),
                createBranch("Systems Engineering"),
                createBranch("Operations Research"),
                createBranch("Supply Chain Engineering"),
                createBranch("Logistics Engineering"),
                createBranch("Engineering Management"),
                createBranch("Technology Management"),
                createBranch("Product Design"),
                createBranch("Tool Engineering"),
                createBranch("Die and Mould Engineering"),
                createBranch("Foundry Technology"),
                createBranch("Welding Engineering"),
                createBranch("Metallurgy and Materials Engineering"),
                createBranch("Corrosion Engineering"),
                createBranch("Materials Science"),
                createBranch("Composite Materials"),
                createBranch("Nanomaterials"),
                createBranch("Semiconductor Engineering"),
                createBranch("Photonics"),
                createBranch("Quantum Engineering"),
                createBranch("Computational Engineering"),
                createBranch("Data Engineering"),
                createBranch("Communication Engineering"),
                createBranch("Network Engineering"),
                createBranch("Cloud Computing"),
                createBranch("Edge Computing"),
                createBranch("Quantum Computing"),
                createBranch("Blockchain Engineering"),
                createBranch("Ethical Hacking"),
                createBranch("Game Engineering"),
                createBranch("User Interface Engineering"),
                createBranch("Mobile Engineering"),
                createBranch("DevOps Engineering"),
                createBranch("Site Reliability Engineering"),
                createBranch("Quality Assurance Engineering"),
                createBranch("Testing Engineering"),
                createBranch("Reliability Engineering"),
                createBranch("Maintainability Engineering"),
                createBranch("Durability Engineering"),
                createBranch("Sustainability Engineering"),
                createBranch("Green Engineering"),
                createBranch("Renewable Energy Engineering"),
                createBranch("Emergency Engineering"),
                createBranch("Resilience Engineering"),
                createBranch("Infrastructure Engineering"),
                createBranch("Urban Engineering"),
                createBranch("Railway Engineering"),
                createBranch("Metro Engineering"),
                createBranch("Airport Engineering"),
                createBranch("Quantitative Engineering"),
                createBranch("Mathematical Engineering")
            );

            branchRepository.saveAll(branches);
            System.out.println("✅ Inserted default engineering branches.");
        } else {
            System.out.println("ℹ️ Branches table already contains data. Skipping insertion.");
        }
    }

    private Branch createBranch(String name) {
        return Branch.builder().name(name).build();
    }
}