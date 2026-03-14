package com.placement.filterbackend.config;

import com.placement.filterbackend.entity.*;
import com.placement.filterbackend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Order(7)
public class PlacementDriveDataLoader implements CommandLineRunner {

    private final PlacementDriveRepository driveRepository;
    private final CollegeRepository collegeRepository;
    private final CompanyRepository companyRepository;
    private final BranchRepository branchRepository;
    private final SkillRepository skillRepository;
    private final LocationRepository locationRepository;

    public PlacementDriveDataLoader(
            PlacementDriveRepository driveRepository,
            CollegeRepository collegeRepository,
            CompanyRepository companyRepository,
            BranchRepository branchRepository,
            SkillRepository skillRepository,
            LocationRepository locationRepository) {
        this.driveRepository = driveRepository;
        this.collegeRepository = collegeRepository;
        this.companyRepository = companyRepository;
        this.branchRepository = branchRepository;
        this.skillRepository = skillRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (driveRepository.count() == 0) {
            System.out.println("🚀 Inserting sample placement drives...");
            
            
            List<College> colleges = collegeRepository.findAll();
            List<Company> companies = companyRepository.findAll();
            List<Branch> allBranches = branchRepository.findAll();
            List<Skill> allSkills = skillRepository.findAll();
            List<Location> allLocations = locationRepository.findAll();
            
            
            Map<String, College> collegeMap = colleges.stream()
                    .collect(Collectors.toMap(College::getName, c -> c));
            
            Map<String, Company> companyMap = companies.stream()
                    .collect(Collectors.toMap(Company::getName, c -> c));
            
            Map<String, Branch> branchMap = allBranches.stream()
                    .collect(Collectors.toMap(Branch::getName, b -> b));
            
            Map<String, Skill> skillMap = allSkills.stream()
                    .collect(Collectors.toMap(Skill::getName, s -> s));
            
            Map<String, Location> locationMap = allLocations.stream()
                    .collect(Collectors.toMap(Location::getName, l -> l));

            List<PlacementDrive> drives = new ArrayList<>();

          
            College ramdeobaba = collegeMap.get("Ramdeobaba University");
            if (ramdeobaba == null) {
                System.out.println("⚠️ Ramdeobaba University not found, skipping its drives");
                return;
            }

          
            Company tcs = getCompany(companyMap, "TCS");
            if (tcs != null) drives.add(createDrive(
                ramdeobaba, tcs, "Assistant System Engineer",
                new BigDecimal("3.5"), 25, "Any", 2, 0,
                LocalDate.of(2026, 1, 15), LocalDate.of(2026, 2, 10), 2026,
                50, new BigDecimal("3.0"), new BigDecimal("3.8"), 2,
                "Open to all branches with 60% throughout",
                "Mass recruitment drive for 2026 batch",
                Arrays.asList("Computer Science and Engineering", "Information Technology", "Electronics and Communication Engineering"),
                Arrays.asList("C", "Java", "SQL"),
                Arrays.asList("Pune", "Mumbai", "Chennai"),
                branchMap, skillMap, locationMap
            ));

            Company infosys = getCompany(companyMap, "Infosys");
            if (infosys != null) drives.add(createDrive(
                ramdeobaba, infosys, "Systems Engineer",
                new BigDecimal("3.8"), 26, "Any", 1, 0,
                LocalDate.of(2026, 1, 20), LocalDate.of(2026, 2, 15), 2026,
                45, new BigDecimal("3.6"), new BigDecimal("4.2"), 2,
                "Minimum 65% in 10th, 12th and engineering",
                "Infosys hiring for Mysore training then deployment",
                Arrays.asList("Computer Science and Engineering", "Information Technology", "Electronics and Communication Engineering"),
                Arrays.asList("Java", "Python", "SQL"),
                Arrays.asList("Bengaluru", "Mysuru", "Pune"),
                branchMap, skillMap, locationMap
            ));

            Company wipro = getCompany(companyMap, "Wipro");
            if (wipro != null) drives.add(createDrive(
                ramdeobaba, wipro, "Project Engineer",
                new BigDecimal("3.5"), 24, "Any", 1, 0,
                LocalDate.of(2026, 2, 5), LocalDate.of(2026, 2, 28), 2026,
                40, new BigDecimal("3.2"), new BigDecimal("3.8"), 3,
                "WILP program - No active backlogs",
                "Wipro TalentNext hiring for 2026 batch",
                Arrays.asList("Computer Science and Engineering", "Information Technology", "Electronics and Communication Engineering", "Mechanical Engineering"),
                Arrays.asList("C", "C++", "Aptitude"),
                Arrays.asList("Bengaluru", "Hyderabad", "Chennai"),
                branchMap, skillMap, locationMap
            ));

            Company techMahindra = getCompany(companyMap, "Tech Mahindra");
            if (techMahindra != null) drives.add(createDrive(
                ramdeobaba, techMahindra, "Software Engineer",
                new BigDecimal("3.5"), 25, "Any", 2, 0,
                LocalDate.of(2026, 2, 10), LocalDate.of(2026, 3, 5), 2026,
                30, new BigDecimal("3.2"), new BigDecimal("3.8"), 2,
                "Good communication skills required",
                "Tech Mahindra hiring for multiple roles",
                Arrays.asList("Computer Science and Engineering", "Information Technology", "Electronics and Communication Engineering"),
                Arrays.asList("Java", "SQL", "Communication"),
                Arrays.asList("Pune", "Mumbai", "Nagpur"),
                branchMap, skillMap, locationMap
            ));

            Company capgemini = getCompany(companyMap, "Capgemini");
            if (capgemini != null) drives.add(createDrive(
                ramdeobaba, capgemini, "Analyst",
                new BigDecimal("3.8"), 25, "Any", 2, 0,
                LocalDate.of(2026, 2, 12), LocalDate.of(2026, 3, 8), 2026,
                35, new BigDecimal("3.6"), new BigDecimal("4.0"), 3,
                "70% in 10th and 12th, 65% in graduation",
                "Capgemini hiring for 2026 batch",
                Arrays.asList("Computer Science and Engineering", "Information Technology", "Electronics and Communication Engineering", "Mechanical Engineering"),
                Arrays.asList("Java", ".NET", "SQL"),
                Arrays.asList("Bengaluru", "Hyderabad", "Mumbai"),
                branchMap, skillMap, locationMap
            ));

            Company cognizant = getCompany(companyMap, "Cognizant");
            if (cognizant != null) drives.add(createDrive(
                ramdeobaba, cognizant, "Programmer Analyst Trainee",
                new BigDecimal("4.0"), 25, "Any", 2, 0,
                LocalDate.of(2026, 2, 18), LocalDate.of(2026, 3, 12), 2026,
                40, new BigDecimal("3.8"), new BigDecimal("4.2"), 2,
                "Strong aptitude and coding skills",
                "CTS hiring for 2026 batch",
                Arrays.asList("Computer Science and Engineering", "Information Technology", "Electronics and Communication Engineering"),
                Arrays.asList("Java", "Python", "SQL"),
                Arrays.asList("Chennai", "Pune", "Kolkata"),
                branchMap, skillMap, locationMap
            ));

            Company siemens = getCompany(companyMap, "Siemens");
            if (siemens != null) drives.add(createDrive(
                ramdeobaba, siemens, "Graduate Engineer Trainee",
                new BigDecimal("5.0"), 24, "M", 2, 0,
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 2, 5), 2026,
                12, new BigDecimal("4.5"), new BigDecimal("5.5"), 3,
                "Mechanical/Electrical/Electronics only",
                "Siemens hiring for core engineering roles",
                Arrays.asList("Mechanical Engineering", "Electrical and Electronics Engineering", "Electronics and Communication Engineering"),
                Arrays.asList("AutoCAD", "MATLAB", "PLC"),
                Arrays.asList("Bengaluru", "Pune", "Mumbai"),
                branchMap, skillMap, locationMap
            ));

            Company larsen = getCompany(companyMap, "Larsen & Toubro");
            if (larsen != null) drives.add(createDrive(
                ramdeobaba, larsen, "GET - Civil",
                new BigDecimal("5.2"), 25, "M", 2, 0,
                LocalDate.of(2026, 1, 18), LocalDate.of(2026, 2, 12), 2026,
                8, new BigDecimal("4.8"), new BigDecimal("5.5"), 3,
                "Civil Engineering only, willing to work on site",
                "L&T construction division hiring",
                Arrays.asList("Civil Engineering"),
                Arrays.asList("AutoCAD", "STAAD Pro", "Project Management"),
                Arrays.asList("Mumbai", "Chennai", "Delhi NCR"),
                branchMap, skillMap, locationMap
            ));

            Company tataMotors = getCompany(companyMap, "Tata Motors");
            if (tataMotors != null) drives.add(createDrive(
                ramdeobaba, tataMotors, "Graduate Engineer Trainee",
                new BigDecimal("4.5"), 24, "M", 2, 0,
                LocalDate.of(2026, 2, 8), LocalDate.of(2026, 3, 3), 2026,
                15, new BigDecimal("4.2"), new BigDecimal("4.8"), 3,
                "Mechanical/Automobile/Production only",
                "Tata Motors hiring for Pune and Jamshedpur plants",
                Arrays.asList("Mechanical Engineering", "Automobile Engineering", "Production Engineering"),
                Arrays.asList("SolidWorks", "CATIA", "GD&T"),
                Arrays.asList("Pune", "Jamshedpur", "Lucknow"),
                branchMap, skillMap, locationMap
            ));

            Company mahindra = getCompany(companyMap, "Mahindra & Mahindra");
            if (mahindra != null) drives.add(createDrive(
                ramdeobaba, mahindra, "GET - Manufacturing",
                new BigDecimal("4.8"), 24, "M", 2, 0,
                LocalDate.of(2026, 2, 20), LocalDate.of(2026, 3, 15), 2026,
                10, new BigDecimal("4.5"), new BigDecimal("5.0"), 3,
                "Mechanical/Production/Automobile only",
                "Mahindra hiring for Nagpur and Mumbai plants",
                Arrays.asList("Mechanical Engineering", "Production Engineering", "Automobile Engineering"),
                Arrays.asList("AutoCAD", "Lean Manufacturing", "Six Sigma"),
                Arrays.asList("Nagpur", "Mumbai", "Chennai"),
                branchMap, skillMap, locationMap
            ));

            Company barclays = getCompany(companyMap, "Barclays");
            if (barclays != null) drives.add(createDrive(
                ramdeobaba, barclays, "Operations Analyst",
                new BigDecimal("5.5"), 24, "Any", 2, 0,
                LocalDate.of(2026, 1, 22), LocalDate.of(2026, 2, 18), 2026,
                8, new BigDecimal("5.0"), new BigDecimal("6.0"), 3,
                "Good communication and analytical skills",
                "BARCLAYS hiring for Pune office",
                Arrays.asList("Computer Science and Engineering", "Information Technology", "Electronics and Communication Engineering", "MBA"),
                Arrays.asList("Excel", "Communication", "Data Analysis"),
                Arrays.asList("Pune", "Mumbai", "Bengaluru"),
                branchMap, skillMap, locationMap
            ));

            Company icici = getCompany(companyMap, "ICICI Bank");
            if (icici != null) drives.add(createDrive(
                ramdeobaba, icici, "Probationary Officer",
                new BigDecimal("6.0"), 25, "Any", 3, 0,
                LocalDate.of(2026, 2, 5), LocalDate.of(2026, 3, 1), 2026,
                12, new BigDecimal("5.5"), new BigDecimal("6.5"), 4,
                "Any graduate with 60% marks",
                "ICICI Bank PO recruitment",
                Arrays.asList("Any Branch"),
                Arrays.asList("Communication", "Aptitude", "Banking Awareness"),
                Arrays.asList("Mumbai", "Delhi NCR", "Bengaluru", "Pune"),
                branchMap, skillMap, locationMap
            ));

            Company hdfc = getCompany(companyMap, "HDFC Bank");
            if (hdfc != null) drives.add(createDrive(
                ramdeobaba, hdfc, "Customer Relationship Officer",
                new BigDecimal("4.2"), 24, "F", 2, 0,
                LocalDate.of(2026, 2, 15), LocalDate.of(2026, 3, 8), 2026,
                15, new BigDecimal("4.0"), new BigDecimal("4.5"), 2,
                "Good communication and presentation skills",
                "HDFC Bank hiring for Nagpur region",
                Arrays.asList("Any Branch"),
                Arrays.asList("Communication", "Sales", "Customer Service"),
                Arrays.asList("Nagpur", "Pune", "Mumbai"),
                branchMap, skillMap, locationMap
            ));

            Company axis = getCompany(companyMap, "Axis Bank");
            if (axis != null) drives.add(createDrive(
                ramdeobaba, axis, "Relationship Manager",
                new BigDecimal("4.5"), 25, "Any", 2, 1,
                LocalDate.of(2026, 2, 22), LocalDate.of(2026, 3, 18), 2026,
                10, new BigDecimal("4.2"), new BigDecimal("4.8"), 3,
                "MBA preferred, good communication",
                "Axis Bank retail banking hiring",
                Arrays.asList("MBA", "Any Graduate"),
                Arrays.asList("Sales", "Communication", "MS Office"),
                Arrays.asList("Mumbai", "Pune", "Nagpur"),
                branchMap, skillMap, locationMap
            ));

            Company jsw = getCompany(companyMap, "JSW Steel");
            if (jsw != null) drives.add(createDrive(
                ramdeobaba, jsw, "Graduate Engineer Trainee",
                new BigDecimal("5.0"), 24, "M", 2, 0,
                LocalDate.of(2026, 1, 5), LocalDate.of(2026, 1, 30), 2026,
                12, new BigDecimal("4.8"), new BigDecimal("5.2"), 3,
                "Metallurgy/Mechanical/Electrical only",
                "JSW Steel hiring for Vijayanagar plant",
                Arrays.asList("Metallurgical Engineering", "Mechanical Engineering", "Electrical and Electronics Engineering"),
                Arrays.asList("Metallurgy", "AutoCAD", "Six Sigma"),
                Arrays.asList("Bengaluru", "Vijayanagar", "Mumbai"),
                branchMap, skillMap, locationMap
            ));

            Company tataSteel = getCompany(companyMap, "Tata Steel");
            if (tataSteel != null) drives.add(createDrive(
                ramdeobaba, tataSteel, "Management Trainee - Technical",
                new BigDecimal("6.2"), 25, "M", 2, 0,
                LocalDate.of(2026, 2, 2), LocalDate.of(2026, 2, 25), 2026,
                8, new BigDecimal("6.0"), new BigDecimal("6.5"), 3,
                "Metallurgy/Mechanical/Electrical with strong academics",
                "Tata Steel technical hiring",
                Arrays.asList("Metallurgical Engineering", "Mechanical Engineering", "Electrical and Electronics Engineering"),
                Arrays.asList("Materials Science", "Thermodynamics", "Quality Control"),
                Arrays.asList("Jamshedpur", "Kalinganagar"),
                branchMap, skillMap, locationMap
            ));

            Company ultratech = getCompany(companyMap, "Ultratech Cement");
            if (ultratech != null) drives.add(createDrive(
                ramdeobaba, ultratech, "GET - Mechanical",
                new BigDecimal("4.2"), 24, "M", 2, 0,
                LocalDate.of(2026, 2, 12), LocalDate.of(2026, 3, 5), 2026,
                10, new BigDecimal("4.0"), new BigDecimal("4.5"), 3,
                "Mechanical/Civil only",
                "Ultratech hiring for various plant locations",
                Arrays.asList("Mechanical Engineering", "Civil Engineering"),
                Arrays.asList("AutoCAD", "Plant Maintenance"),
                Arrays.asList("Mumbai", "Ahmadabad", "Nagpur"),
                branchMap, skillMap, locationMap
            ));

            Company godrej = getCompany(companyMap, "Godrej");
            if (godrej != null) drives.add(createDrive(
                ramdeobaba, godrej, "Management Trainee",
                new BigDecimal("5.5"), 24, "Any", 2, 0,
                LocalDate.of(2026, 2, 18), LocalDate.of(2026, 3, 12), 2026,
                8, new BigDecimal("5.2"), new BigDecimal("5.8"), 3,
                "MBA/Engineering with strong academics",
                "Godrej group hiring across divisions",
                Arrays.asList("Mechanical Engineering", "MBA", "Production Engineering"),
                Arrays.asList("Leadership", "Communication", "Project Management"),
                Arrays.asList("Mumbai", "Pune", "Bengaluru"),
                branchMap, skillMap, locationMap
            ));

            Company persistent = getCompany(companyMap, "Persistent Systems");
            if (persistent != null) drives.add(createDrive(
                ramdeobaba, persistent, "Software Developer",
                new BigDecimal("5.0"), 24, "Any", 2, 0,
                LocalDate.of(2026, 1, 12), LocalDate.of(2026, 2, 5), 2026,
                15, new BigDecimal("4.5"), new BigDecimal("5.5"), 3,
                "Strong coding skills required",
                "Persistent hiring for Nagpur and Pune",
                Arrays.asList("Computer Science and Engineering", "Information Technology", "Electronics and Communication Engineering"),
                Arrays.asList("Java", "Python", "JavaScript"),
                Arrays.asList("Nagpur", "Pune", "Bengaluru"),
                branchMap, skillMap, locationMap
            ));

            Company zensar = getCompany(companyMap, "Zensar");
            if (zensar != null) drives.add(createDrive(
                ramdeobaba, zensar, "Software Engineer Trainee",
                new BigDecimal("3.8"), 24, "Any", 2, 0,
                LocalDate.of(2026, 1, 25), LocalDate.of(2026, 2, 18), 2026,
                20, new BigDecimal("3.5"), new BigDecimal("4.0"), 2,
                "Good academic record, no backlogs",
                "Zensar hiring for Pune and Nagpur",
                Arrays.asList("Computer Science and Engineering", "Information Technology", "Electronics and Communication Engineering"),
                Arrays.asList("Java", "SQL", "Web Technologies"),
                Arrays.asList("Pune", "Nagpur", "Bengaluru"),
                branchMap, skillMap, locationMap
            ));

            Company cyient = getCompany(companyMap, "Cyient");
            if (cyient != null) drives.add(createDrive(
                ramdeobaba, cyient, "Engineer Trainee",
                new BigDecimal("4.0"), 24, "Any", 2, 0,
                LocalDate.of(2026, 2, 8), LocalDate.of(2026, 3, 2), 2026,
                18, new BigDecimal("3.8"), new BigDecimal("4.2"), 2,
                "Mechanical/Electronics/CS preferred",
                "Cyient hiring for Hyderabad and Pune",
                Arrays.asList("Mechanical Engineering", "Electronics and Communication Engineering", "Computer Science and Engineering"),
                Arrays.asList("AutoCAD", "Java", "C++"),
                Arrays.asList("Hyderabad", "Pune", "Bengaluru"),
                branchMap, skillMap, locationMap
            ));

            Company lti = getCompany(companyMap, "LTI");
            if (lti != null) drives.add(createDrive(
                ramdeobaba, lti, "Graduate Trainee",
                new BigDecimal("3.6"), 24, "Any", 2, 0,
                LocalDate.of(2026, 2, 15), LocalDate.of(2026, 3, 10), 2026,
                22, new BigDecimal("3.4"), new BigDecimal("3.8"), 2,
                "Open to all branches",
                "LTI mass recruitment drive",
                Arrays.asList("All Branches"),
                Arrays.asList("Aptitude", "Communication"),
                Arrays.asList("Mumbai", "Pune", "Chennai"),
                branchMap, skillMap, locationMap
            ));

            Company hcl = getCompany(companyMap, "HCL Technologies");
            if (hcl != null) drives.add(createDrive(
                ramdeobaba, hcl, "Service Desk Analyst",
                new BigDecimal("2.8"), 23, "Any", 2, 0,
                LocalDate.of(2026, 3, 1), LocalDate.of(2026, 3, 22), 2026,
                30, new BigDecimal("2.5"), new BigDecimal("3.0"), 2,
                "Good communication skills, night shift",
                "HCL hiring for Nagpur SEZ",
                Arrays.asList("Any Branch"),
                Arrays.asList("Communication", "Basic IT Skills"),
                Arrays.asList("Nagpur"),
                branchMap, skillMap, locationMap
            ));

            Company kpit = getCompany(companyMap, "KPIT");
            if (kpit != null) drives.add(createDrive(
                ramdeobaba, kpit, "Trainee Engineer",
                new BigDecimal("4.5"), 24, "Any", 2, 0,
                LocalDate.of(2026, 2, 20), LocalDate.of(2026, 3, 15), 2026,
                12, new BigDecimal("4.2"), new BigDecimal("4.8"), 3,
                "Automotive domain knowledge preferred",
                "KPIT hiring for Pune and Nagpur",
                Arrays.asList("Electronics and Communication Engineering", "Computer Science and Engineering", "Mechanical Engineering"),
                Arrays.asList("C", "Embedded Systems", "MATLAB"),
                Arrays.asList("Pune", "Nagpur", "Bengaluru"),
                branchMap, skillMap, locationMap
            ));

            Company torrent = getCompany(companyMap, "Torrent Power");
            if (torrent != null) drives.add(createDrive(
                ramdeobaba, torrent, "GET - Electrical",
                new BigDecimal("4.8"), 24, "M", 2, 0,
                LocalDate.of(2026, 1, 28), LocalDate.of(2026, 2, 20), 2026,
                6, new BigDecimal("4.5"), new BigDecimal("5.0"), 3,
                "Electrical Engineering only",
                "Torrent Power hiring for Gujarat and Maharashtra",
                Arrays.asList("Electrical and Electronics Engineering", "Power Engineering"),
                Arrays.asList("Power Systems", "Electrical Machines"),
                Arrays.asList("Ahmadabad", "Dahej", "Nagpur"),
                branchMap, skillMap, locationMap
            ));

            
            if (tcs != null) drives.add(createDrive(
                ramdeobaba, tcs, "Assistant System Engineer",
                new BigDecimal("3.2"), 24, "Any", 2, 0,
                LocalDate.of(2024, 1, 10), LocalDate.of(2024, 2, 5), 2024,
                45, new BigDecimal("2.8"), new BigDecimal("3.4"), 2,
                "Open to all branches, 60% throughout",
                "TCS 2024 mass recruitment",
                Arrays.asList("Computer Science and Engineering", "Information Technology", "Electronics and Communication Engineering"),
                Arrays.asList("C", "Java", "SQL"),
                Arrays.asList("Pune", "Mumbai", "Chennai"),
                branchMap, skillMap, locationMap
            ));

            if (infosys != null) drives.add(createDrive(
                ramdeobaba, infosys, "Systems Engineer",
                new BigDecimal("3.5"), 25, "Any", 1, 0,
                LocalDate.of(2024, 2, 1), LocalDate.of(2024, 2, 25), 2024,
                40, new BigDecimal("3.3"), new BigDecimal("3.9"), 2,
                "Minimum 65% in academics",
                "Infosys 2024 hiring",
                Arrays.asList("Computer Science and Engineering", "Information Technology", "Electronics and Communication Engineering"),
                Arrays.asList("Java", "Python", "SQL"),
                Arrays.asList("Bengaluru", "Mysuru", "Pune"),
                branchMap, skillMap, locationMap
            ));

            if (wipro != null) drives.add(createDrive(
                ramdeobaba, wipro, "Project Engineer",
                new BigDecimal("3.2"), 23, "Any", 1, 0,
                LocalDate.of(2023, 11, 5), LocalDate.of(2023, 11, 28), 2023,
                35, new BigDecimal("3.0"), new BigDecimal("3.5"), 2,
                "WILP program - no backlogs",
                "Wipro 2023 batch",
                Arrays.asList("Computer Science and Engineering", "Information Technology", "Electronics"),
                Arrays.asList("C", "Aptitude"),
                Arrays.asList("Bengaluru", "Hyderabad"),
                branchMap, skillMap, locationMap
            ));

            if (capgemini != null) drives.add(createDrive(
                ramdeobaba, capgemini, "Analyst",
                new BigDecimal("3.5"), 24, "Any", 2, 0,
                LocalDate.of(2023, 9, 12), LocalDate.of(2023, 10, 5), 2023,
                28, new BigDecimal("3.3"), new BigDecimal("3.8"), 3,
                "70% in 10th/12th, 65% in graduation",
                "Capgemini 2023 drive",
                Arrays.asList("Computer Science", "IT", "Electronics"),
                Arrays.asList("Java", ".NET", "SQL"),
                Arrays.asList("Bengaluru", "Hyderabad", "Mumbai"),
                branchMap, skillMap, locationMap
            ));

            if (larsen != null) drives.add(createDrive(
                ramdeobaba, larsen, "GET - Civil",
                new BigDecimal("4.0"), 24, "M", 2, 0,
                LocalDate.of(2023, 8, 15), LocalDate.of(2023, 9, 10), 2023,
                6, new BigDecimal("3.8"), new BigDecimal("4.3"), 3,
                "Civil Engineering only",
                "L&T 2023 recruitment",
                Arrays.asList("Civil Engineering"),
                Arrays.asList("AutoCAD", "STAAD Pro"),
                Arrays.asList("Mumbai", "Chennai", "Delhi NCR"),
                branchMap, skillMap, locationMap
            ));

            
            College ycce = collegeMap.get("Yeshwantrao Chavan College of Engineering");
            if (ycce != null) {
             
                if (tcs != null) drives.add(createDrive(
                    ycce, tcs, "Assistant System Engineer",
                    new BigDecimal("3.4"), 25, "Any", 2, 0,
                    LocalDate.of(2026, 2, 10), LocalDate.of(2026, 3, 5), 2026,
                    40, new BigDecimal("3.0"), new BigDecimal("3.6"), 2,
                    "Open to all branches, 60% throughout",
                    "TCS mass recruitment",
                    Arrays.asList("Computer Science and Engineering", "Information Technology", "Electronics and Communication Engineering", "Mechanical Engineering", "Civil Engineering"),
                    Arrays.asList("C", "Java", "SQL"),
                    Arrays.asList("Pune", "Mumbai", "Chennai"),
                    branchMap, skillMap, locationMap
                ));

                if (infosys != null) drives.add(createDrive(
                    ycce, infosys, "Systems Engineer",
                    new BigDecimal("3.6"), 25, "Any", 1, 0,
                    LocalDate.of(2026, 2, 15), LocalDate.of(2026, 3, 8), 2026,
                    35, new BigDecimal("3.4"), new BigDecimal("3.8"), 2,
                    "65% in academics",
                    "Infosys hiring for Mysuru training",
                    Arrays.asList("Computer Science and Engineering", "Information Technology", "Electronics and Communication Engineering"),
                    Arrays.asList("Java", "Python", "SQL"),
                    Arrays.asList("Bengaluru", "Mysuru", "Pune"),
                    branchMap, skillMap, locationMap
                ));

                if (wipro != null) drives.add(createDrive(
                    ycce, wipro, "Project Engineer",
                    new BigDecimal("3.2"), 24, "Any", 1, 0,
                    LocalDate.of(2026, 2, 18), LocalDate.of(2026, 3, 12), 2026,
                    30, new BigDecimal("3.0"), new BigDecimal("3.5"), 2,
                    "WILP program - no active backlogs",
                    "Wipro hiring for 2026 batch",
                    Arrays.asList("Computer Science and Engineering", "Information Technology", "Electronics and Communication Engineering"),
                    Arrays.asList("C", "Aptitude"),
                    Arrays.asList("Bengaluru", "Hyderabad", "Chennai"),
                    branchMap, skillMap, locationMap
                ));

                if (techMahindra != null) drives.add(createDrive(
                    ycce, techMahindra, "Software Engineer",
                    new BigDecimal("3.2"), 25, "Any", 2, 0,
                    LocalDate.of(2026, 2, 22), LocalDate.of(2026, 3, 15), 2026,
                    25, new BigDecimal("3.0"), new BigDecimal("3.5"), 2,
                    "Good communication skills",
                    "Tech Mahindra hiring",
                    Arrays.asList("Computer Science and Engineering", "Information Technology", "Electronics and Communication Engineering"),
                    Arrays.asList("Java", "SQL", "Communication"),
                    Arrays.asList("Pune", "Mumbai", "Nagpur"),
                    branchMap, skillMap, locationMap
                ));

                if (capgemini != null) drives.add(createDrive(
                    ycce, capgemini, "Analyst",
                    new BigDecimal("3.6"), 25, "Any", 2, 0,
                    LocalDate.of(2026, 2, 25), LocalDate.of(2026, 3, 18), 2026,
                    28, new BigDecimal("3.4"), new BigDecimal("3.8"), 3,
                    "70% in 10th/12th, 65% in graduation",
                    "Capgemini hiring",
                    Arrays.asList("Computer Science and Engineering", "Information Technology", "Electronics and Communication Engineering"),
                    Arrays.asList("Java", ".NET", "SQL"),
                    Arrays.asList("Bengaluru", "Hyderabad", "Mumbai"),
                    branchMap, skillMap, locationMap
                ));

                if (larsen != null) drives.add(createDrive(
                    ycce, larsen, "GET - Civil",
                    new BigDecimal("4.5"), 25, "M", 2, 0,
                    LocalDate.of(2026, 1, 20), LocalDate.of(2026, 2, 15), 2026,
                    6, new BigDecimal("4.2"), new BigDecimal("4.8"), 3,
                    "Civil Engineering only",
                    "L&T construction division",
                    Arrays.asList("Civil Engineering"),
                    Arrays.asList("AutoCAD", "STAAD Pro"),
                    Arrays.asList("Mumbai", "Chennai", "Delhi NCR"),
                    branchMap, skillMap, locationMap
                ));

                if (mahindra != null) drives.add(createDrive(
                    ycce, mahindra, "GET - Manufacturing",
                    new BigDecimal("4.2"), 24, "M", 2, 0,
                    LocalDate.of(2026, 2, 5), LocalDate.of(2026, 2, 28), 2026,
                    8, new BigDecimal("4.0"), new BigDecimal("4.5"), 3,
                    "Mechanical/Production only",
                    "Mahindra hiring for Nagpur plant",
                    Arrays.asList("Mechanical Engineering", "Production Engineering"),
                    Arrays.asList("AutoCAD", "Lean Manufacturing"),
                    Arrays.asList("Nagpur", "Mumbai", "Pune"),
                    branchMap, skillMap, locationMap
                ));

                if (tataMotors != null) drives.add(createDrive(
                    ycce, tataMotors, "Graduate Engineer Trainee",
                    new BigDecimal("4.0"), 24, "M", 2, 0,
                    LocalDate.of(2026, 2, 12), LocalDate.of(2026, 3, 5), 2026,
                    10, new BigDecimal("3.8"), new BigDecimal("4.2"), 3,
                    "Mechanical/Automobile/Production",
                    "Tata Motors hiring",
                    Arrays.asList("Mechanical Engineering", "Automobile Engineering", "Production Engineering"),
                    Arrays.asList("SolidWorks", "CATIA"),
                    Arrays.asList("Pune", "Jamshedpur", "Lucknow"),
                    branchMap, skillMap, locationMap
                ));

                if (hdfc != null) drives.add(createDrive(
                    ycce, hdfc, "Customer Service Officer",
                    new BigDecimal("3.8"), 24, "F", 2, 0,
                    LocalDate.of(2026, 2, 18), LocalDate.of(2026, 3, 10), 2026,
                    12, new BigDecimal("3.5"), new BigDecimal("4.0"), 2,
                    "Good communication skills",
                    "HDFC Bank hiring for Nagpur",
                    Arrays.asList("Any Branch"),
                    Arrays.asList("Communication", "Customer Service"),
                    Arrays.asList("Nagpur", "Pune"),
                    branchMap, skillMap, locationMap
                ));

                if (persistent != null) drives.add(createDrive(
                    ycce, persistent, "Software Developer",
                    new BigDecimal("4.2"), 24, "Any", 2, 0,
                    LocalDate.of(2026, 2, 20), LocalDate.of(2026, 3, 15), 2026,
                    12, new BigDecimal("4.0"), new BigDecimal("4.5"), 3,
                    "Strong coding skills",
                    "Persistent hiring",
                    Arrays.asList("Computer Science and Engineering", "Information Technology", "Electronics and Communication Engineering"),
                    Arrays.asList("Java", "Python", "JavaScript"),
                    Arrays.asList("Nagpur", "Pune", "Bengaluru"),
                    branchMap, skillMap, locationMap
                ));

                
                if (tcs != null) drives.add(createDrive(
                    ycce, tcs, "Assistant System Engineer",
                    new BigDecimal("3.0"), 24, "Any", 2, 0,
                    LocalDate.of(2024, 2, 5), LocalDate.of(2024, 2, 28), 2024,
                    35, new BigDecimal("2.7"), new BigDecimal("3.3"), 2,
                    "Open to all branches",
                    "TCS 2024 drive",
                    Arrays.asList("Computer Science", "IT", "Electronics"),
                    Arrays.asList("C", "Java"),
                    Arrays.asList("Pune", "Mumbai"),
                    branchMap, skillMap, locationMap
                ));

                if (infosys != null) drives.add(createDrive(
                    ycce, infosys, "Systems Engineer",
                    new BigDecimal("3.3"), 25, "Any", 1, 0,
                    LocalDate.of(2023, 10, 10), LocalDate.of(2023, 11, 5), 2023,
                    30, new BigDecimal("3.0"), new BigDecimal("3.5"), 2,
                    "65% in academics",
                    "Infosys 2023 hiring",
                    Arrays.asList("Computer Science", "IT", "Electronics"),
                    Arrays.asList("Java", "Python"),
                    Arrays.asList("Bengaluru", "Pune"),
                    branchMap, skillMap, locationMap
                ));
            }

          
            College iitBombay = collegeMap.get("IIT Bombay");
            College iitDelhi = collegeMap.get("IIT Delhi");
            College iitMadras = collegeMap.get("IIT Madras");
            College iitKharagpur = collegeMap.get("IIT Kharagpur");
            College iitKanpur = collegeMap.get("IIT Kanpur");
            
            
            if (iitBombay != null) {
                Company google = getCompany(companyMap, "Google");
                if (google != null) drives.add(createDrive(
                    iitBombay, google, "Software Engineer",
                    new BigDecimal("7.5"), 26, "Any", 0, 0,
                    LocalDate.of(2025, 12, 1), LocalDate.of(2025, 12, 15), 2026,
                    8, new BigDecimal("35.0"), new BigDecimal("45.0"), 5,
                    "Strong DSA and problem solving skills",
                    "Google hiring for Bengaluru and Hyderabad offices",
                    Arrays.asList("Computer Science and Engineering", "Information Technology", "Mathematics"),
                    Arrays.asList("Java", "Python", "Data Structures", "Algorithms", "System Design"),
                    Arrays.asList("Bengaluru", "Hyderabad", "Gurugram"),
                    branchMap, skillMap, locationMap
                ));

                Company goldman = getCompany(companyMap, "Goldman Sachs");
                if (goldman != null) drives.add(createDrive(
                    iitBombay, goldman, "Summer Analyst",
                    new BigDecimal("8.0"), 25, "Any", 1, 0,
                    LocalDate.of(2025, 11, 25), LocalDate.of(2025, 12, 10), 2026,
                    6, new BigDecimal("30.0"), new BigDecimal("40.0"), 4,
                    "Strong analytical and coding skills",
                    "Goldman Sachs technology division",
                    Arrays.asList("Computer Science and Engineering", "Electronics and Communication Engineering", "Mathematics"),
                    Arrays.asList("Java", "Python", "SQL", "Data Structures"),
                    Arrays.asList("Bengaluru", "Mumbai", "Hyderabad"),
                    branchMap, skillMap, locationMap
                ));

              
                if (google != null) drives.add(createDrive(
                    iitBombay, google, "Software Engineer",
                    new BigDecimal("7.0"), 25, "Any", 0, 0,
                    LocalDate.of(2024, 11, 15), LocalDate.of(2024, 12, 5), 2025,
                    7, new BigDecimal("32.0"), new BigDecimal("40.0"), 5,
                    "Strong problem solving",
                    "Google 2024 hiring",
                    Arrays.asList("Computer Science", "Mathematics"),
                    Arrays.asList("Java", "Python", "Algorithms"),
                    Arrays.asList("Bengaluru", "Hyderabad"),
                    branchMap, skillMap, locationMap
                ));

                if (goldman != null) drives.add(createDrive(
                    iitBombay, goldman, "Summer Analyst",
                    new BigDecimal("7.5"), 24, "Any", 1, 0,
                    LocalDate.of(2023, 11, 20), LocalDate.of(2023, 12, 8), 2024,
                    5, new BigDecimal("28.0"), new BigDecimal("35.0"), 4,
                    "Analytical skills",
                    "Goldman 2023 drive",
                    Arrays.asList("Computer Science", "Mathematics"),
                    Arrays.asList("Java", "Python", "SQL"),
                    Arrays.asList("Bengaluru", "Mumbai"),
                    branchMap, skillMap, locationMap
                ));
            }

            
            if (iitDelhi != null) {
                Company microsoft = getCompany(companyMap, "Microsoft");
                if (microsoft != null) drives.add(createDrive(
                    iitDelhi, microsoft, "Software Engineer",
                    new BigDecimal("7.8"), 26, "Any", 0, 0,
                    LocalDate.of(2025, 12, 2), LocalDate.of(2025, 12, 16), 2026,
                    10, new BigDecimal("38.0"), new BigDecimal("48.0"), 5,
                    "Strong coding and system design skills",
                    "Microsoft hiring for India Development Center",
                    Arrays.asList("Computer Science and Engineering", "Information Technology", "Electronics and Communication Engineering"),
                    Arrays.asList("C++", "C#", "Data Structures", "Algorithms", "System Design"),
                    Arrays.asList("Bengaluru", "Hyderabad", "Noida"),
                    branchMap, skillMap, locationMap
                ));

                Company amex = getCompany(companyMap, "American Express");
                if (amex != null) drives.add(createDrive(
                    iitDelhi, amex, "Technology Analyst",
                    new BigDecimal("6.5"), 25, "Any", 1, 0,
                    LocalDate.of(2025, 11, 28), LocalDate.of(2025, 12, 12), 2026,
                    5, new BigDecimal("25.0"), new BigDecimal("32.0"), 4,
                    "Good academic record, strong coding",
                    "Amex technology hiring",
                    Arrays.asList("Computer Science and Engineering", "Information Technology"),
                    Arrays.asList("Java", "Python", "SQL", "Data Structures"),
                    Arrays.asList("Gurugram", "Bengaluru", "Chennai"),
                    branchMap, skillMap, locationMap
                ));

                
                if (microsoft != null) drives.add(createDrive(
                    iitDelhi, microsoft, "Software Engineer",
                    new BigDecimal("7.2"), 25, "Any", 0, 0,
                    LocalDate.of(2024, 11, 10), LocalDate.of(2024, 12, 1), 2025,
                    8, new BigDecimal("35.0"), new BigDecimal("42.0"), 5,
                    "Coding and system design",
                    "Microsoft 2024",
                    Arrays.asList("Computer Science"),
                    Arrays.asList("C++", "Data Structures"),
                    Arrays.asList("Bengaluru", "Noida"),
                    branchMap, skillMap, locationMap
                ));

                if (amex != null) drives.add(createDrive(
                    iitDelhi, amex, "Technology Analyst",
                    new BigDecimal("6.0"), 24, "Any", 1, 0,
                    LocalDate.of(2023, 10, 20), LocalDate.of(2023, 11, 10), 2024,
                    4, new BigDecimal("22.0"), new BigDecimal("28.0"), 4,
                    "Good coding skills",
                    "Amex 2023",
                    Arrays.asList("Computer Science"),
                    Arrays.asList("Java", "SQL"),
                    Arrays.asList("Gurugram", "Bengaluru"),
                    branchMap, skillMap, locationMap
                ));
            }

            
            if (iitMadras != null) {
                Company amazon = getCompany(companyMap, "Amazon");
                if (amazon != null) drives.add(createDrive(
                    iitMadras, amazon, "Software Development Engineer",
                    new BigDecimal("7.2"), 26, "Any", 0, 0,
                    LocalDate.of(2025, 12, 3), LocalDate.of(2025, 12, 17), 2026,
                    15, new BigDecimal("32.0"), new BigDecimal("42.0"), 5,
                    "Strong problem solving skills",
                    "Amazon hiring for multiple locations",
                    Arrays.asList("Computer Science and Engineering", "Information Technology", "Electronics and Communication Engineering"),
                    Arrays.asList("Java", "Python", "Data Structures", "Algorithms", "System Design"),
                    Arrays.asList("Bengaluru", "Hyderabad", "Chennai", "Pune"),
                    branchMap, skillMap, locationMap
                ));

                Company qualcomm = getCompany(companyMap, "Qualcomm");
                if (qualcomm != null) drives.add(createDrive(
                    iitMadras, qualcomm, "Engineer - Hardware",
                    new BigDecimal("7.0"), 25, "Any", 1, 0,
                    LocalDate.of(2025, 11, 30), LocalDate.of(2025, 12, 14), 2026,
                    8, new BigDecimal("28.0"), new BigDecimal("35.0"), 4,
                    "VLSI/Embedded Systems specialization",
                    "Qualcomm hiring for hardware roles",
                    Arrays.asList("Electronics and Communication Engineering", "Electrical and Electronics Engineering", "VLSI Design"),
                    Arrays.asList("Verilog", "VHDL", "Embedded C", "Digital Design"),
                    Arrays.asList("Bengaluru", "Hyderabad", "Chennai"),
                    branchMap, skillMap, locationMap
                ));

              
                if (amazon != null) drives.add(createDrive(
                    iitMadras, amazon, "SDE",
                    new BigDecimal("6.8"), 25, "Any", 0, 0,
                    LocalDate.of(2024, 11, 5), LocalDate.of(2024, 11, 25), 2025,
                    12, new BigDecimal("30.0"), new BigDecimal("38.0"), 4,
                    "Problem solving",
                    "Amazon 2024",
                    Arrays.asList("Computer Science", "Electronics"),
                    Arrays.asList("Java", "Algorithms"),
                    Arrays.asList("Chennai", "Bengaluru"),
                    branchMap, skillMap, locationMap
                ));

                if (qualcomm != null) drives.add(createDrive(
                    iitMadras, qualcomm, "Hardware Engineer",
                    new BigDecimal("6.5"), 24, "Any", 1, 0,
                    LocalDate.of(2023, 11, 15), LocalDate.of(2023, 12, 5), 2024,
                    6, new BigDecimal("25.0"), new BigDecimal("30.0"), 3,
                    "VLSI design",
                    "Qualcomm 2023",
                    Arrays.asList("Electronics"),
                    Arrays.asList("Verilog", "VHDL"),
                    Arrays.asList("Bengaluru", "Hyderabad"),
                    branchMap, skillMap, locationMap
                ));
            }

         
            if (iitKharagpur != null) {
                Company texas = getCompany(companyMap, "Texas Instruments");
                if (texas != null) drives.add(createDrive(
                    iitKharagpur, texas, "Analog Design Engineer",
                    new BigDecimal("7.5"), 25, "Any", 1, 0,
                    LocalDate.of(2025, 12, 5), LocalDate.of(2025, 12, 19), 2026,
                    6, new BigDecimal("30.0"), new BigDecimal("38.0"), 4,
                    "Strong analog circuit design knowledge",
                    "TI hiring for Bengaluru R&D center",
                    Arrays.asList("Electronics and Communication Engineering", "Electrical and Electronics Engineering", "VLSI Design"),
                    Arrays.asList("Analog Circuits", "Verilog", "SPICE", "VLSI"),
                    Arrays.asList("Bengaluru", "Chennai"),
                    branchMap, skillMap, locationMap
                ));

                Company tower = getCompany(companyMap, "Tower Research Capital");
                if (tower != null) drives.add(createDrive(
                    iitKharagpur, tower, "Quantitative Trader",
                    new BigDecimal("9.0"), 26, "Any", 0, 0,
                    LocalDate.of(2025, 12, 1), LocalDate.of(2025, 12, 8), 2026,
                    3, new BigDecimal("45.0"), new BigDecimal("60.0"), 4,
                    "Strong mathematical and coding skills",
                    "HFT firm hiring for Gurugram office",
                    Arrays.asList("Computer Science and Engineering", "Mathematics", "Electronics and Communication Engineering"),
                    Arrays.asList("C++", "Python", "Probability", "Statistics"),
                    Arrays.asList("Gurugram", "Mumbai", "Bengaluru"),
                    branchMap, skillMap, locationMap
                ));

                
                if (texas != null) drives.add(createDrive(
                    iitKharagpur, texas, "Analog Design Engineer",
                    new BigDecimal("7.0"), 24, "Any", 1, 0,
                    LocalDate.of(2024, 11, 10), LocalDate.of(2024, 12, 1), 2025,
                    5, new BigDecimal("28.0"), new BigDecimal("35.0"), 4,
                    "Analog circuits",
                    "TI 2024",
                    Arrays.asList("Electronics"),
                    Arrays.asList("Analog Circuits", "SPICE"),
                    Arrays.asList("Bengaluru"),
                    branchMap, skillMap, locationMap
                ));
            }

            
            if (iitKanpur != null) {
                Company samsung = getCompany(companyMap, "Samsung");
                if (samsung != null) drives.add(createDrive(
                    iitKanpur, samsung, "Software Engineer - R&D",
                    new BigDecimal("7.2"), 25, "Any", 1, 0,
                    LocalDate.of(2025, 12, 4), LocalDate.of(2025, 12, 18), 2026,
                    12, new BigDecimal("28.0"), new BigDecimal("36.0"), 4,
                    "Strong coding and OS concepts",
                    "Samsung R&D hiring for Noida and Bengaluru",
                    Arrays.asList("Computer Science and Engineering", "Electronics and Communication Engineering", "Information Technology"),
                    Arrays.asList("C++", "Java", "Python", "OS", "Data Structures"),
                    Arrays.asList("Noida", "Bengaluru", "Delhi NCR"),
                    branchMap, skillMap, locationMap
                ));

                Company jpm = getCompany(companyMap, "JPMorgan Chase");
                if (jpm != null) drives.add(createDrive(
                    iitKanpur, jpm, "Software Engineer Program",
                    new BigDecimal("7.8"), 26, "Any", 1, 0,
                    LocalDate.of(2025, 11, 27), LocalDate.of(2025, 12, 11), 2026,
                    7, new BigDecimal("32.0"), new BigDecimal("40.0"), 4,
                    "Strong coding and analytical skills",
                    "JP Morgan hiring for Mumbai and Bengaluru",
                    Arrays.asList("Computer Science and Engineering", "Information Technology", "Electronics and Communication Engineering"),
                    Arrays.asList("Java", "Python", "SQL", "Data Structures"),
                    Arrays.asList("Mumbai", "Bengaluru", "Hyderabad"),
                    branchMap, skillMap, locationMap
                ));

              
                if (samsung != null) drives.add(createDrive(
                    iitKanpur, samsung, "R&D Engineer",
                    new BigDecimal("6.8"), 24, "Any", 1, 0,
                    LocalDate.of(2024, 11, 20), LocalDate.of(2024, 12, 10), 2025,
                    10, new BigDecimal("26.0"), new BigDecimal("32.0"), 3,
                    "Coding and OS",
                    "Samsung 2024",
                    Arrays.asList("Computer Science"),
                    Arrays.asList("C++", "OS"),
                    Arrays.asList("Noida", "Bengaluru"),
                    branchMap, skillMap, locationMap
                ));
            }

          
            College vit = collegeMap.get("VIT Vellore");
            if (vit != null) {
                Company cisco = getCompany(companyMap, "Cisco");
                if (cisco != null) drives.add(createDrive(
                    vit, cisco, "Network Engineer",
                    new BigDecimal("6.0"), 24, "Any", 2, 0,
                    LocalDate.of(2026, 1, 10), LocalDate.of(2026, 2, 5), 2026,
                    12, new BigDecimal("18.0"), new BigDecimal("24.0"), 4,
                    "Networking protocols knowledge",
                    "Cisco hiring for Bengaluru",
                    Arrays.asList("Computer Science and Engineering", "Electronics and Communication Engineering", "Information Technology"),
                    Arrays.asList("Networking", "C", "Python"),
                    Arrays.asList("Bengaluru", "Chennai", "Pune"),
                    branchMap, skillMap, locationMap
                ));

              
                if (cisco != null) drives.add(createDrive(
                    vit, cisco, "Network Engineer",
                    new BigDecimal("5.5"), 23, "Any", 2, 0,
                    LocalDate.of(2024, 11, 15), LocalDate.of(2024, 12, 5), 2025,
                    10, new BigDecimal("16.0"), new BigDecimal("20.0"), 3,
                    "Networking basics",
                    "Cisco 2024",
                    Arrays.asList("Computer Science", "Electronics"),
                    Arrays.asList("Networking", "C"),
                    Arrays.asList("Bengaluru", "Chennai"),
                    branchMap, skillMap, locationMap
                ));
            }

        
            College bits = collegeMap.get("BITS Pilani");
            if (bits != null) {
                Company atlassian = getCompany(companyMap, "Atlassian");
                if (atlassian != null) drives.add(createDrive(
                    bits, atlassian, "Software Developer",
                    new BigDecimal("6.5"), 24, "Any", 1, 0,
                    LocalDate.of(2026, 1, 15), LocalDate.of(2026, 2, 8), 2026,
                    6, new BigDecimal("22.0"), new BigDecimal("28.0"), 4,
                    "Strong product development skills",
                    "Atlassian hiring for Bengaluru",
                    Arrays.asList("Computer Science and Engineering", "Information Technology"),
                    Arrays.asList("Java", "Python", "System Design", "Algorithms"),
                    Arrays.asList("Bengaluru", "Hyderabad"),
                    branchMap, skillMap, locationMap
                ));

                
                if (atlassian != null) drives.add(createDrive(
                    bits, atlassian, "Software Developer",
                    new BigDecimal("6.0"), 23, "Any", 1, 0,
                    LocalDate.of(2024, 10, 20), LocalDate.of(2024, 11, 10), 2025,
                    5, new BigDecimal("20.0"), new BigDecimal("25.0"), 3,
                    "Product development",
                    "Atlassian 2024",
                    Arrays.asList("Computer Science"),
                    Arrays.asList("Java", "Algorithms"),
                    Arrays.asList("Bengaluru"),
                    branchMap, skillMap, locationMap
                ));
            }

            driveRepository.saveAll(drives);
            System.out.println("✅ Inserted " + drives.size() + " sample placement drives.");
        } else {
            System.out.println("ℹ️ Placement drives table already contains data. Skipping insertion.");
        }
    }

    
    private Company getCompany(Map<String, Company> companyMap, String name) {
        Company c = companyMap.get(name);
        if (c == null) {
            System.out.println("⚠️ Company not found: " + name + " – skipping this drive.");
        }
        return c;
    }

    private PlacementDrive createDrive(
            College college, Company company, String role,
            BigDecimal minCgpa, Integer maxAge, String genderPreference,
            Integer maxBacklogs, Integer requiredExperienceMonths,
            LocalDate pptDate, LocalDate placementDate, Integer year,
            Integer noOfPeopleHired, BigDecimal packageMinLpa, BigDecimal packageMaxLpa,
            Integer noOfRounds, String eligibilityNotes, String description,
            List<String> branchNames, List<String> skillNames, List<String> locationNames,
            Map<String, Branch> branchMap, Map<String, Skill> skillMap, Map<String, Location> locationMap) {
        
        PlacementDrive drive = new PlacementDrive();
        drive.setCollege(college);
        drive.setCompany(company);
        drive.setRole(role);
        drive.setMinCgpa(minCgpa);
        drive.setMaxAge(maxAge);
        
        switch (genderPreference) {
            case "M": drive.setGenderPreference(PlacementDrive.GenderPreference.M); break;
            case "F": drive.setGenderPreference(PlacementDrive.GenderPreference.F); break;
            default: drive.setGenderPreference(PlacementDrive.GenderPreference.Any);
        }
        
        drive.setMaxBacklogs(maxBacklogs);
        drive.setRequiredExperienceMonths(requiredExperienceMonths);
        drive.setPptDate(pptDate);
        drive.setPlacementDate(placementDate);
        drive.setYear(year);
        drive.setNoOfPeopleHired(noOfPeopleHired);
        drive.setPackageMinLpa(packageMinLpa);
        drive.setPackageMaxLpa(packageMaxLpa);
        drive.setNoOfRounds(noOfRounds);
        drive.setEligibilityNotes(eligibilityNotes);
        drive.setDescription(description);
        
        
        Set<Branch> branches = new HashSet<>();
        boolean anyBranch = false;
        for (String branchName : branchNames) {
            if (branchName.equalsIgnoreCase("Any Branch") || branchName.equalsIgnoreCase("All Branches")) {
                anyBranch = true;
                break; 
            } else {
                Branch branch = branchMap.get(branchName);
                if (branch != null) branches.add(branch);
            }
        }
        
        drive.setBranches(branches); 
        
        
        Set<Skill> skills = new HashSet<>();
        for (String skillName : skillNames) {
            Skill skill = skillMap.get(skillName);
            if (skill != null) skills.add(skill);
        }
        drive.setSkills(skills);
        
       
        Set<Location> locations = new HashSet<>();
        for (String locationName : locationNames) {
            Location location = locationMap.get(locationName);
            if (location != null) locations.add(location);
        }
        drive.setLocations(locations);
        
        return drive;
    }
}