package com.placement.filterbackend.config;

import com.placement.filterbackend.entity.Company;
import com.placement.filterbackend.repository.CompanyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Order(5)
public class CompanyDataLoader implements CommandLineRunner {

    private final CompanyRepository companyRepository;

    public CompanyDataLoader(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (companyRepository.count() == 0) {
            Set<String> uniqueCompanyNames = new LinkedHashSet<>(Arrays.asList(
                
                "Google", "Microsoft", "Amazon", "Meta", "Apple", "Netflix", "Adobe", "Intel", "IBM", "Cisco",
                "Oracle", "Salesforce", "VMware", "SAP", "Accenture", "Deloitte", "PwC", "EY", "KPMG",
                "Goldman Sachs", "JPMorgan Chase", "Morgan Stanley", "Bank of America", "Wells Fargo",
                "Citibank", "HSBC", "Barclays", "Standard Chartered", "Deutsche Bank", "UBS", "Credit Suisse",
                "American Express", "Visa", "Mastercard", "PayPal", "Stripe", "Square", "Shopify", "Flipkart",
                "Amazon India", "Walmart", "Target", "Alibaba", "Tencent", "Baidu", "JD.com", "Samsung", "LG",
                "Sony", "Panasonic", "Toshiba", "Hitachi", "Mitsubishi", "Honda", "Toyota", "Ford",
                "General Motors", "Tesla", "Boeing", "Airbus", "Lockheed Martin", "Northrop Grumman", "Raytheon",
                "General Electric", "Siemens", "Philips", "Johnson & Johnson", "Pfizer", "Novartis", "Roche",
                "Merck", "AstraZeneca", "GlaxoSmithKline", "Sanofi", "Bayer", "Coca-Cola", "PepsiCo", "Nestlé",
                "Unilever", "Procter & Gamble", "Kimberly-Clark", "Colgate-Palmolive", "L'Oréal", "Nike", "Adidas",
                "Puma", "Zara", "H&M", "McDonald's", "Starbucks", "Domino's", "Pizza Hut", "KFC", "Burger King",
                "Taco Bell", "Uber", "Lyft", "Ola", "Airbnb", "Booking.com", "Expedia", "TripAdvisor", "Zoom",
                "Slack", "Dropbox", "Box", "Atlassian", "GitHub", "GitLab", "Red Hat", "Canonical", "Mozilla",
                "WordPress", "Automattic", "Wix", "Squarespace", "GoDaddy", "Cloudflare", "Akamai", "Fastly",
                "Netlify", "Vercel", "Heroku", "DigitalOcean", "Linode", "Vultr", "AWS", "Google Cloud",
                "Microsoft Azure", "IBM Cloud", "Oracle Cloud", "Alibaba Cloud", "Tencent Cloud", "ServiceNow",
                "Workday", "SAP SuccessFactors", "Oracle HCM", "Zoho", "Freshworks", "Druva", "Nutanix", "Citrix",
                "F5 Networks", "Palo Alto Networks", "Fortinet", "Check Point", "CrowdStrike", "McAfee", "Symantec",
                "Trend Micro", "Sophos", "Kaspersky", "Avast", "Bitdefender", "Malwarebytes", "Zscaler", "Okta",
                "Ping Identity", "ForgeRock", "SailPoint", "CyberArk", "BeyondTrust", "Thycotic", "Centrify",
                "OneLogin", "Auth0", "Duo Security", "FireEye", "Mandiant", "Rapid7", "Qualys", "Tenable", "Nessus",
                "Burp Suite", "PortSwigger", "Acunetix", "Netsparker", "Invicti", "HCL Technologies", "Infosys",
                "Wipro", "TCS", "Tech Mahindra", "Cognizant", "Capgemini", "DXC Technology", "LTI", "Mindtree",
                "Mphasis", "Hexaware", "Persistent Systems", "Cyient", "Coforge", "NIIT", "Zensar", "Sonata Software",
                "KPIT", "L&T Infotech", "L&T Technology Services",

                
                // Indian Banks & Financial
                "HDFC Bank", "ICICI Bank", "Axis Bank", "Kotak Mahindra Bank", "Yes Bank", "IndusInd Bank",
                "State Bank of India", "Bank of Baroda", "Punjab National Bank", "Canara Bank", "LIC",
                "HDFC Life", "ICICI Lombard", "Bajaj Allianz", "SBI Life", "Motilal Oswal", "Zerodha", "Groww",
                "Upstox", "CRED", "PolicyBazaar", "Paisabazaar",

                // Indian IT & Consulting
                "Birlasoft", "Mastek", "Datamatics", "Atos", "Sopra Steria", "CGI", "NTT Data", "Fujitsu", "NEC",
                "Sasken", "Subex", "Mindteck", "Nelito", "Onward Technologies", "R Systems", "Tata Technologies",
                "Virtusa", "Xoriant", "Yash Technologies", "Zycus", "eInfochips", "EagleView", "Nihilent",

                // Core Manufacturing & Engineering
                "Tata Motors", "Mahindra & Mahindra", "Bajaj Auto", "Hero MotoCorp", "TVS Motors", "Ashok Leyland",
                "Maruti Suzuki", "Hyundai", "Kia", "Larsen & Toubro", "L&T Construction", "Tata Steel", "JSW Steel",
                "Hindalco", "Vedanta", "Adani", "Reliance Industries", "Jio", "Airtel", "Vodafone Idea", "BSNL",
                "Tata Power", "NTPC", "Power Grid", "IOCL", "BPCL", "HPCL", "GAIL", "ONGC", "Coal India", "SAIL",
                "Ambuja Cements", "Ultratech", "ACC", "Lafarge", "Asian Paints", "Berger Paints", "Godrej",
                "Pidilite", "ITC", "Britannia", "Dabur", "Marico", "Emami",

                // Pharma & Healthcare
                "Cipla", "Dr. Reddy's", "Sun Pharma", "Lupin", "Aurobindo", "Torrent", "Cadila", "Divis", "Biocon",
                "Mankind", "Alkem", "Glenmark", "Apollo Hospitals", "Fortis", "Max Healthcare", "Narayana Health",
                "Manipal Hospitals",

                // Indian Startups & Consumer Tech
                "Oyo", "Swiggy", "Zomato", "Paytm", "PhonePe", "Google Pay", "Amazon Pay", "Razorpay", "Nykaa",
                "Myntra", "Meesho", "Udaan", "Curefit", "Ola Electric", "Ather", "Rapido", "Dunzo", "Urban Company",
                "ShareChat", "Dailyhunt", "InMobi", "Practo", "1mg", "PharmEasy", "BigBasket", "Blinkit", "Zepto",
                "Licious", "FirstCry", "Pepperfry", "Urban Ladder", "Wakefit", "Boat", "Noise", "OnePlus", "Xiaomi",
                "Vivo", "Oppo", "Realme", "Motorola", "Dell", "HP", "Lenovo", "Acer", "Asus", "Seagate",
                "Western Digital", "SanDisk", "Micron", "SK Hynix", "NVIDIA", "AMD", "Qualcomm", "MediaTek",
                "Broadcom", "Texas Instruments", "Analog Devices", "NXP", "Infineon", "STMicroelectronics", "ARM",
                "Synopsys", "Cadence", "MathWorks", "Ansys", "Dassault Systèmes", "PTC", "Autodesk", "SUSE",
                "MongoDB", "Elastic", "Databricks", "Snowflake", "Confluent", "HashiCorp", "Twilio", "SendGrid"
            ));

            List<Company> companies = uniqueCompanyNames.stream()
                    .map(this::createCompany)
                    .collect(Collectors.toList());

            companyRepository.saveAll(companies);
            System.out.println("✅ Inserted " + companies.size() + " default companies.");
        } else {
            System.out.println("ℹ️ Companies table already contains data. Skipping insertion.");
        }
    }

    private Company createCompany(String name) {
        return Company.builder().name(name).build();
    }
}