package com.placement.filterbackend.config;

import com.placement.filterbackend.entity.Skill;
import com.placement.filterbackend.repository.SkillRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Order(2)
public class SkillDataLoader implements CommandLineRunner {

    private final SkillRepository skillRepository;

    public SkillDataLoader(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (skillRepository.count() == 0) {
            List<Skill> skills = Arrays.asList(
          
                createSkill("C", "Programming Language"),
                createSkill("C++", "Programming Language"),
                createSkill("C#", "Programming Language"),
                createSkill("Java", "Programming Language"),
                createSkill("Python", "Programming Language"),
                createSkill("JavaScript", "Programming Language"),
                createSkill("TypeScript", "Programming Language"),
                createSkill("Go", "Programming Language"),
                createSkill("Rust", "Programming Language"),
                createSkill("Swift", "Programming Language"),
                createSkill("Kotlin", "Programming Language"),
                createSkill("PHP", "Programming Language"),
                createSkill("Ruby", "Programming Language"),
                createSkill("Perl", "Programming Language"),
                createSkill("Scala", "Programming Language"),
                createSkill("Haskell", "Programming Language"),
                createSkill("Elixir", "Programming Language"),
                createSkill("Clojure", "Programming Language"),
                createSkill("Dart", "Programming Language"),
                createSkill("R", "Programming Language"),
                createSkill("MATLAB", "Programming Language"),
                createSkill("SQL", "Query Language"),
                createSkill("PL/SQL", "Query Language"),
                createSkill("Assembly", "Programming Language"),
                createSkill("VHDL", "Hardware Description"),
                createSkill("Verilog", "Hardware Description"),

                
                createSkill("React", "Frontend Framework"),
                createSkill("Angular", "Frontend Framework"),
                createSkill("Vue.js", "Frontend Framework"),
                createSkill("Svelte", "Frontend Framework"),
                createSkill("jQuery", "JavaScript Library"),
                createSkill("Bootstrap", "CSS Framework"),
                createSkill("Tailwind CSS", "CSS Framework"),
                createSkill("Material-UI", "UI Library"),
                createSkill("Node.js", "Backend Runtime"),
                createSkill("Express.js", "Backend Framework"),
                createSkill("Django", "Backend Framework"),
                createSkill("Flask", "Backend Framework"),
                createSkill("Spring Boot", "Backend Framework"),
                createSkill("ASP.NET", "Backend Framework"),
                createSkill("Ruby on Rails", "Backend Framework"),
                createSkill("Laravel", "Backend Framework"),
                createSkill("FastAPI", "Backend Framework"),
                createSkill("GraphQL", "API Technology"),
                createSkill("REST APIs", "API Technology"),

               
                createSkill("MySQL", "Database"),
                createSkill("PostgreSQL", "Database"),
                createSkill("Oracle DB", "Database"),
                createSkill("Microsoft SQL Server", "Database"),
                createSkill("SQLite", "Database"),
                createSkill("MongoDB", "NoSQL Database"),
                createSkill("Cassandra", "NoSQL Database"),
                createSkill("Redis", "NoSQL Database"),
                createSkill("Elasticsearch", "Search Engine"),
                createSkill("Firebase", "Backend as a Service"),
                createSkill("DynamoDB", "NoSQL Database"),
                createSkill("Couchbase", "NoSQL Database"),
                createSkill("MariaDB", "Database"),
                createSkill("HBase", "NoSQL Database"),
                createSkill("Neo4j", "Graph Database"),

               
                createSkill("AWS", "Cloud Platform"),
                createSkill("Microsoft Azure", "Cloud Platform"),
                createSkill("Google Cloud Platform", "Cloud Platform"),
                createSkill("IBM Cloud", "Cloud Platform"),
                createSkill("Oracle Cloud", "Cloud Platform"),
                createSkill("Docker", "Containerization"),
                createSkill("Kubernetes", "Container Orchestration"),
                createSkill("Jenkins", "CI/CD"),
                createSkill("GitLab CI", "CI/CD"),
                createSkill("GitHub Actions", "CI/CD"),
                createSkill("Terraform", "Infrastructure as Code"),
                createSkill("Ansible", "Configuration Management"),
                createSkill("Puppet", "Configuration Management"),
                createSkill("Chef", "Configuration Management"),
                createSkill("Prometheus", "Monitoring"),
                createSkill("Grafana", "Monitoring"),
                createSkill("Nagios", "Monitoring"),
                createSkill("Splunk", "Log Management"),
                createSkill("ELK Stack", "Log Management"),
                createSkill("New Relic", "APM"),
                createSkill("Datadog", "Monitoring"),

                
                createSkill("Git", "Version Control"),
                createSkill("GitHub", "Version Control"),
                createSkill("GitLab", "Version Control"),
                createSkill("Bitbucket", "Version Control"),
                createSkill("SVN", "Version Control"),
                createSkill("Mercurial", "Version Control"),

              
                createSkill("Linux", "Operating System"),
                createSkill("Unix", "Operating System"),
                createSkill("Windows", "Operating System"),
                createSkill("macOS", "Operating System"),
                createSkill("Shell Scripting", "Scripting"),

                
                createSkill("Machine Learning", "Data Science"),
                createSkill("Deep Learning", "Data Science"),
                createSkill("Natural Language Processing", "Data Science"),
                createSkill("Computer Vision", "Data Science"),
                createSkill("TensorFlow", "ML Framework"),
                createSkill("PyTorch", "ML Framework"),
                createSkill("Keras", "ML Framework"),
                createSkill("Scikit-learn", "ML Library"),
                createSkill("Pandas", "Data Analysis"),
                createSkill("NumPy", "Data Analysis"),
                createSkill("Matplotlib", "Data Visualization"),
                createSkill("Seaborn", "Data Visualization"),
                createSkill("Tableau", "Data Visualization"),
                createSkill("Power BI", "Data Visualization"),
                createSkill("Apache Spark", "Big Data"),
                createSkill("Hadoop", "Big Data"),
                createSkill("Hive", "Big Data"),
                createSkill("Pig", "Big Data"),
                createSkill("Kafka", "Streaming"),
                createSkill("Airflow", "Workflow"),

               
                createSkill("Android Development", "Mobile"),
                createSkill("iOS Development", "Mobile"),
                createSkill("Flutter", "Mobile Framework"),
                createSkill("React Native", "Mobile Framework"),
                createSkill("Xamarin", "Mobile Framework"),
                createSkill("SwiftUI", "Mobile Framework"),

                
                createSkill("Embedded C", "Embedded Systems"),
                createSkill("Arduino", "Embedded Systems"),
                createSkill("Raspberry Pi", "Embedded Systems"),
                createSkill("IoT", "Internet of Things"),
                createSkill("RTOS", "Embedded Systems"),
                createSkill("ARM", "Embedded Architecture"),

                
                createSkill("Network Security", "Cybersecurity"),
                createSkill("Ethical Hacking", "Cybersecurity"),
                createSkill("Penetration Testing", "Cybersecurity"),
                createSkill("Cryptography", "Cybersecurity"),
                createSkill("Security Auditing", "Cybersecurity"),
                createSkill("Firewall", "Cybersecurity"),
                createSkill("SIEM", "Cybersecurity"),
                createSkill("Incident Response", "Cybersecurity"),

                
                createSkill("TCP/IP", "Networking"),
                createSkill("DNS", "Networking"),
                createSkill("HTTP/HTTPS", "Networking"),
                createSkill("Load Balancing", "Networking"),
                createSkill("VPN", "Networking"),
                createSkill("Cisco", "Networking"),
                createSkill("Routing & Switching", "Networking"),

                
                createSkill("Communication", "Soft Skill"),
                createSkill("Teamwork", "Soft Skill"),
                createSkill("Leadership", "Soft Skill"),
                createSkill("Problem Solving", "Soft Skill"),
                createSkill("Critical Thinking", "Soft Skill"),
                createSkill("Time Management", "Soft Skill"),
                createSkill("Adaptability", "Soft Skill"),
                createSkill("Creativity", "Soft Skill"),
                createSkill("Emotional Intelligence", "Soft Skill"),
                createSkill("Conflict Resolution", "Soft Skill"),

                
                createSkill("Agile", "Methodology"),
                createSkill("Scrum", "Methodology"),
                createSkill("Kanban", "Methodology"),
                createSkill("Waterfall", "Methodology"),
                createSkill("JIRA", "Project Management"),
                createSkill("Confluence", "Project Management"),
                createSkill("Trello", "Project Management"),
                createSkill("Asana", "Project Management"),
                createSkill("MS Project", "Project Management"),

                
                createSkill("Unit Testing", "Testing"),
                createSkill("Integration Testing", "Testing"),
                createSkill("Selenium", "Testing Tool"),
                createSkill("JUnit", "Testing Tool"),
                createSkill("TestNG", "Testing Tool"),
                createSkill("Cypress", "Testing Tool"),
                createSkill("Postman", "Testing Tool"),
                createSkill("SoapUI", "Testing Tool"),
                createSkill("JMeter", "Testing Tool"),

                
                createSkill("Data Structures", "Computer Science"),
                createSkill("Algorithms", "Computer Science"),
                createSkill("Operating Systems", "Computer Science"),
                createSkill("Computer Networks", "Computer Science"),
                createSkill("Database Management", "Computer Science"),
                createSkill("System Design", "Computer Science"),
                createSkill("Microservices", "Architecture"),
                createSkill("Event-Driven Architecture", "Architecture"),
                createSkill("Serverless", "Architecture"),
                createSkill("OOP", "Programming Paradigm"),
                createSkill("Functional Programming", "Programming Paradigm"),
                createSkill("Design Patterns", "Software Engineering"),
                createSkill("UML", "Modeling"),
                createSkill("Requirements Analysis", "Business Analysis"),
                createSkill("Technical Writing", "Documentation")
            );

            skillRepository.saveAll(skills);
            System.out.println("✅ Inserted " + skills.size() + " default skills.");
        } else {
            System.out.println("ℹ️ Skills table already contains data. Skipping insertion.");
        }
    }

    private Skill createSkill(String name, String category) {
        return Skill.builder().name(name).category(category).build();
    }
}