package com.placement.filterbackend.config;

import com.placement.filterbackend.entity.College;
import com.placement.filterbackend.entity.Company;
import com.placement.filterbackend.entity.CompanyResource;
import com.placement.filterbackend.repository.CollegeRepository;
import com.placement.filterbackend.repository.CompanyRepository;
import com.placement.filterbackend.repository.CompanyResourceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Order(9)
public class ResourceDataLoader implements CommandLineRunner {

    private final CompanyResourceRepository resourceRepository;
    private final CollegeRepository collegeRepository;
    private final CompanyRepository companyRepository;

    public ResourceDataLoader(CompanyResourceRepository resourceRepository,
                              CollegeRepository collegeRepository,
                              CompanyRepository companyRepository) {
        this.resourceRepository = resourceRepository;
        this.collegeRepository = collegeRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (resourceRepository.count() == 0) {
            System.out.println("🚀 Inserting sample resources...");

            List<College> colleges = collegeRepository.findAll();
            List<Company> companies = companyRepository.findAll();

            Map<String, College> collegeMap = colleges.stream()
                    .collect(Collectors.toMap(College::getName, c -> c));
            Map<String, Company> companyMap = companies.stream()
                    .collect(Collectors.toMap(Company::getName, c -> c));

            List<CompanyResource> resources = new ArrayList<>();

       
            College ramdeobaba = collegeMap.get("Ramdeobaba University");
            if (ramdeobaba != null) {
                
                resources.add(createResource(ramdeobaba, companyMap.get("TCS"),
                        "TCS Interview Experience (2025)",
                        "I went through the TCS recruitment process in Jan 2025. There were two rounds: an online aptitude test and a technical interview. The aptitude test had questions on quantitative aptitude, logical reasoning, and verbal ability. The technical interview was about 30 minutes long. They asked me about my projects, basic OOP concepts, and some SQL queries. The interviewers were friendly and gave hints when I was stuck. I was selected for the Assistant System Engineer role."));

                
                resources.add(createResource(ramdeobaba, companyMap.get("Infosys"),
                        "Infosys Systems Engineer – Selection Process",
                        "Infosys visited our campus in Feb 2025. The process had three stages: online test (aptitude + coding), technical interview, and HR round. The online test was moderate; coding questions were on arrays and strings. The technical interview focused on Java, databases, and project experience. The HR round was more about communication and willingness to relocate. Overall a smooth process."));

                
                resources.add(createResource(ramdeobaba, companyMap.get("Wipro"),
                        "Wipro WILP Program – My Journey",
                        "I applied for Wipro's Work Integrated Learning Program (WILP) in 2024. The online test included aptitude, verbal, and coding (C and Python). After clearing that, there was a business communication test. Finally, an interview with a technical panel. They asked about my academic projects and basics of networking. Got selected and now working from Nagpur."));

                
                resources.add(createResource(ramdeobaba, companyMap.get("Persistent Systems"),
                        "Persistent Systems Interview (Software Developer)",
                        "Persistent came to our campus in Sep 2024. Round 1 was an online test with aptitude and Java coding. Round 2 was a technical interview where they asked about Spring Boot, Hibernate, and REST APIs. Round 3 was HR. The interviewers were very knowledgeable and pushed me to think. I received an offer with a decent package."));

                
                resources.add(createResource(ramdeobaba, companyMap.get("Siemens"),
                        "Siemens GET Interview – Core Electrical",
                        "Siemens visited for Graduate Engineer Trainee (Electrical) in 2023. The selection had an online test (electrical machines, power systems, and basic electronics) followed by a technical interview. They asked about transformer design, induction motors, and some MATLAB questions. Panel was experienced and gave practical scenarios. I didn't get selected but it was a great learning experience."));

                
                resources.add(createResource(ramdeobaba, companyMap.get("Larsen & Toubro"),
                        "L&T Construction – Interview Tips",
                        "I attended L&T's campus drive for civil engineers in 2023. The process: written test (engineering mechanics, SOM, and general aptitude), then a technical interview. They focused on site-related problems, concrete technology, and project management. They also asked about willingness to work on remote sites. I made it through and worked on a highway project."));

                
                resources.add(createResource(ramdeobaba, companyMap.get("Tata Motors"),
                        "Tata Motors GET (Mechanical) – Preparation Guide",
                        "Tata Motors recruitment for Mechanical GET had an online test covering thermodynamics, strength of materials, and manufacturing processes. The interview was technical and HR combined. They asked about my internship in an automotive workshop and basics of IC engines. Tips: be thorough with your final year project and core subjects."));

                
                resources.add(createResource(ramdeobaba, companyMap.get("HDFC Bank"),
                        "HDFC Bank – Customer Service Officer Interview",
                        "The process was simple: online test (banking awareness, reasoning, English) and a personal interview. The interview was more about communication skills and knowledge of banking products. They asked why I wanted to join banking. Selected for Nagpur branch."));

                
                resources.add(createResource(ramdeobaba, companyMap.get("Capgemini"),
                        "Capgemini Analyst Interview Experience",
                        "Capgemini visited in Jan 2025. Online test had aptitude, essay writing, and technical MCQ. The interview was a mix of technical and HR. They asked about SQL, Java basics, and my favorite project. Panel was friendly. Got offer and joined Pune office."));

                
                resources.add(createResource(ramdeobaba, companyMap.get("Cognizant"),
                        "Cognizant Programmer Analyst Trainee – How to Prepare",
                        "CTS came to our campus in 2024. The online test had sections on aptitude, verbal, and coding (C/Java). The interview focused on programming concepts, data structures, and puzzles. I recommend practicing basic algorithms and being confident with your resume."));
            } else {
                System.out.println("⚠️ Ramdeobaba University not found, skipping its resources.");
            }

            
            College ycce = collegeMap.get("Yeshwantrao Chavan College of Engineering");
            if (ycce != null) {
                resources.add(createResource(ycce, companyMap.get("Tech Mahindra"),
                        "Tech Mahindra Software Engineer – Round‑by‑Round",
                        "Tech Mahindra visited YCCE in 2023. Process: online test (aptitude, verbal, coding), technical interview, and HR. The technical interview was relatively easy – they asked about C, SQL, and a puzzle. The HR round checked communication and flexibility. Got selected and working remotely."));

                resources.add(createResource(ycce, companyMap.get("Infosys"),
                        "Infosys – Tips for Clearing the Interview",
                        "Infosys came to YCCE in 2024. The key is to be strong in at least one programming language (Java/Python). They also look for good communication in the HR round. Practice previous year papers for the online test."));
            }

            College iitBombay = collegeMap.get("IIT Bombay");
            if (iitBombay != null) {
                resources.add(createResource(iitBombay, companyMap.get("Google"),
                        "Google Interview – The Hardest Yet Most Rewarding",
                        "I applied through campus placement at IIT Bombay. The process had 5 rounds: phone screen, 3 technical interviews, and a hiring committee. Questions ranged from algorithms and data structures to system design. They expected clean code and deep understanding of time complexity. It was intense but I learned a lot. Finally got an offer for SDE role."));
            }

            College iitDelhi = collegeMap.get("IIT Delhi");
            if (iitDelhi != null) {
                resources.add(createResource(iitDelhi, companyMap.get("Microsoft"),
                        "Microsoft Interview Experience (IDC)",
                        "Microsoft visited IIT Delhi in 2024. There were 4 rounds: coding on a whiteboard, system design, and two technical + manager rounds. They focused on problem‑solving and how you approach ambiguous questions. The interviewers were collaborative. I received an offer for the Bengaluru office."));
            }

            College vit = collegeMap.get("VIT Vellore");
            if (vit != null) {
                resources.add(createResource(vit, companyMap.get("Amazon"),
                        "Amazon SDE Interview – Leadership Principles Matter",
                        "Amazon came to VIT in 2024. The process: online coding test, then 3 interviews (2 technical + 1 bar raiser). They heavily emphasize leadership principles – be ready with stories from your projects. Technical questions included DSA, OS, and design problems."));
            }

            College bits = collegeMap.get("BITS Pilani");
            if (bits != null) {
                resources.add(createResource(bits, companyMap.get("Atlassian"),
                        "Atlassian – Product Development Interview",
                        "Atlassian visited BITS Pilani in 2023. The interview had a coding round and two technical interviews focusing on system design and object‑oriented programming. They value clean code and testing. Overall a great experience."));
            }

            resourceRepository.saveAll(resources);
            System.out.println("✅ Inserted " + resources.size() + " sample resources.");
        } else {
            System.out.println("ℹ️ Resources table already contains data. Skipping insertion.");
        }
    }

    private CompanyResource createResource(College college, Company company,
                                           String title, String content) {
        CompanyResource resource = new CompanyResource();
        resource.setCollege(college);
        resource.setCompany(company);
        resource.setTitle(title);
        resource.setContent(content);
        resource.setPosterName(null); 
        return resource;
    }
}