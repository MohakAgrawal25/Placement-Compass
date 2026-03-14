package com.placement.filterbackend.config;

import com.placement.filterbackend.entity.College;
import com.placement.filterbackend.entity.Company;
import com.placement.filterbackend.entity.CompanyReview;
import com.placement.filterbackend.repository.CollegeRepository;
import com.placement.filterbackend.repository.CompanyRepository;
import com.placement.filterbackend.repository.CompanyReviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Order(8)
public class ReviewDataLoader implements CommandLineRunner {

    private final CompanyReviewRepository reviewRepository;
    private final CollegeRepository collegeRepository;
    private final CompanyRepository companyRepository;

    public ReviewDataLoader(CompanyReviewRepository reviewRepository,
                            CollegeRepository collegeRepository,
                            CompanyRepository companyRepository) {
        this.reviewRepository = reviewRepository;
        this.collegeRepository = collegeRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (reviewRepository.count() == 0) {
            System.out.println("🚀 Inserting sample reviews...");

            List<College> colleges = collegeRepository.findAll();
            List<Company> companies = companyRepository.findAll();

            Map<String, College> collegeMap = colleges.stream()
                    .collect(Collectors.toMap(College::getName, c -> c));
            Map<String, Company> companyMap = companies.stream()
                    .collect(Collectors.toMap(Company::getName, c -> c));

            List<CompanyReview> reviews = new ArrayList<>();

            
            College ramdeobaba = collegeMap.get("Ramdeobaba University");
            if (ramdeobaba != null) {
                
                reviews.add(createReview(ramdeobaba, companyMap.get("TCS"), 2025, 4,
                        "The selection process was smooth. Two rounds: online test and technical interview. Questions were mostly from C, Java, and SQL. HR was friendly."));
                reviews.add(createReview(ramdeobaba, companyMap.get("TCS"), 2024, 3,
                        "Online test was moderate difficulty. Interview asked about projects and basic OOP concepts. Got selected but package is low."));

                
                reviews.add(createReview(ramdeobaba, companyMap.get("Infosys"), 2025, 5,
                        "Great experience! The recruitment team was very supportive. Aptitude and coding rounds were fair. Selected for Systems Engineer role."));
                reviews.add(createReview(ramdeobaba, companyMap.get("Infosys"), 2024, 4,
                        "Process took two days. Group discussion followed by technical interview. Focused on Java and SQL. Overall good."));

                
                reviews.add(createReview(ramdeobaba, companyMap.get("Wipro"), 2025, 3,
                        "Online test was easy. Interview asked about basics of C and some HR questions. Waited long for results but finally got offer."));

                
                reviews.add(createReview(ramdeobaba, companyMap.get("Persistent Systems"), 2024, 5,
                        "Very well organised. Two technical rounds and one HR. Questions on Java, Spring Boot, and database design. Got offer with good package."));

              
                reviews.add(createReview(ramdeobaba, companyMap.get("Siemens"), 2023, 4,
                        "Core company hiring for GET role. Technical interview focused on electrical machines and basic programming. Panel was knowledgeable."));

                
                reviews.add(createReview(ramdeobaba, companyMap.get("Tata Motors"), 2024, 4,
                        "Selection process: aptitude, group discussion, and technical interview. Questions on automobile engineering and thermodynamics. Good exposure."));

            
                reviews.add(createReview(ramdeobaba, companyMap.get("Larsen & Toubro"), 2023, 5,
                        "Rigorous selection. Written test, then two technical interviews. They focused on civil engineering fundamentals and site experience. Panel was respectful."));

               
                reviews.add(createReview(ramdeobaba, companyMap.get("HDFC Bank"), 2025, 3,
                        "Online test followed by interview. Mostly banking awareness and communication skills. Selected for customer service role."));
            } else {
                System.out.println("⚠️ Ramdeobaba University not found, skipping its reviews.");
            }

           
            College ycce = collegeMap.get("Yeshwantrao Chavan College of Engineering");
            if (ycce != null) {
                reviews.add(createReview(ycce, companyMap.get("Capgemini"), 2024, 4,
                        "Online test was tricky. Interview asked about projects and basic .NET. HR round was casual. Got selected."));
                reviews.add(createReview(ycce, companyMap.get("Tech Mahindra"), 2023, 3,
                        "Process was quick. Aptitude and technical interview. They asked about SQL queries and communication skills. Offer came within a week."));
            }

            
            College iitBombay = collegeMap.get("IIT Bombay");
            if (iitBombay != null) {
                reviews.add(createReview(iitBombay, companyMap.get("Google"), 2025, 5,
                        "Challenging but rewarding. Four rounds: phone screen, onsite coding, system design, and hiring committee. Interviewers were very smart. Got offer!"));
            }

            
            College vit = collegeMap.get("VIT Vellore");
            if (vit != null) {
                reviews.add(createReview(vit, companyMap.get("Amazon"), 2024, 4,
                        "Three rounds: online coding, technical interview, and bar raiser. Questions on DSA and leadership principles. Good experience."));
            }

            
            College bits = collegeMap.get("BITS Pilani");
            if (bits != null) {
                reviews.add(createReview(bits, companyMap.get("Microsoft"), 2024, 5,
                        "Amazing experience. Interview focused on algorithms, system design, and problem solving. Panel was friendly. Got selected for SDE role."));
            }

            reviewRepository.saveAll(reviews);
            System.out.println("✅ Inserted " + reviews.size() + " sample reviews.");
        } else {
            System.out.println("ℹ️ Reviews table already contains data. Skipping insertion.");
        }
    }

    private CompanyReview createReview(College college, Company company, Integer driveYear,
                                       Integer rating, String feedback) {
        CompanyReview review = new CompanyReview();
        review.setCollege(college);
        review.setCompany(company);
        review.setDriveYear(driveYear);
        review.setRating(rating);
        review.setFeedback(feedback);
        review.setPosterName(null); 
        return review;
    }
}