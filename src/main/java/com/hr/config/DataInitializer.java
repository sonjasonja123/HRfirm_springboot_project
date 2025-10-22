
package com.hr.config;

import com.hr.model.*;
import com.hr.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private PositionRepository positionRepository;
    
    @Autowired
    private CandidateRepository candidateRepository;
    
    @Autowired
    private InterviewRepository interviewRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Always create users if they don't exist
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setName("Admin");
            admin.setSurname("User");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            userRepository.save(admin);
            System.out.println("Admin user created: username=admin, password=admin123");
        }

        if (userRepository.findByUsername("hr").isEmpty()) {
            User hr = new User();
            hr.setName("HR");
            hr.setSurname("Manager");
            hr.setUsername("hr");
            hr.setPassword(passwordEncoder.encode("hr123"));
            hr.setRole("HR_MANAGER");
            userRepository.save(hr);
            System.out.println("HR user created: username=hr, password=hr123");
        }

        if (userRepository.findByUsername("worker1").isEmpty()) {
            User worker = new User();
            worker.setName("Petar");
            worker.setSurname("Petrovic");
            worker.setUsername("worker1");
            worker.setPassword(passwordEncoder.encode("worker123"));
            worker.setRole("USER");
            userRepository.save(worker);
            System.out.println("Worker user created: username=worker1, password=worker123");
        }

        // Create or update sample companies with credentials
        Optional<Company> existingTechCompany = companyRepository.findByName("Tech Solutions Inc.");
        Company company1;
        if (existingTechCompany.isPresent()) {
            company1 = existingTechCompany.get();
            company1.setUsername("techsolutions");
            company1.setPassword(passwordEncoder.encode("tech123"));
            company1 = companyRepository.save(company1);
            System.out.println("Company UPDATED with credentials: username=techsolutions, password=tech123");
        } else {
            company1 = new Company();
            company1.setName("Tech Solutions Inc.");
            company1.setIndustry("Technology");
            company1.setPib("12345678");
            company1.setContact("contact@techsolutions.com");
            company1.setUsername("techsolutions");
            company1.setPassword(passwordEncoder.encode("tech123"));
            company1 = companyRepository.save(company1);
            System.out.println("Company CREATED: username=techsolutions, password=tech123");
        }

        Optional<Company> existingFinanceCompany = companyRepository.findByName("Finance Corp");
        Company company2;
        if (existingFinanceCompany.isPresent()) {
            company2 = existingFinanceCompany.get();
            company2.setUsername("financecorp");
            company2.setPassword(passwordEncoder.encode("finance123"));
            company2 = companyRepository.save(company2);
            System.out.println("Company UPDATED with credentials: username=financecorp, password=finance123");
        } else {
            company2 = new Company();
            company2.setName("Finance Corp");
            company2.setIndustry("Finance");
            company2.setPib("87654321");
            company2.setContact("hr@financecorp.com");
            company2.setUsername("financecorp");
            company2.setPassword(passwordEncoder.encode("finance123"));
            company2 = companyRepository.save(company2);
            System.out.println("Company CREATED: username=financecorp, password=finance123");
        }
        
        // Check if other sample data already exists (pozicije, kandidati, itd)
        if (positionRepository.count() > 0) {
            return;
        }

        // Create sample positions
        Position position1 = new Position();
        position1.setName("Senior Java Developer");
        position1.setDetails("We are looking for an experienced Java developer with Spring Boot knowledge.");
        position1.setOpen(true);
        position1.setCompany(company1);
        position1.setDateFrom(LocalDate.now());
        position1.setDateTo(LocalDate.now().plusMonths(3));
        position1 = positionRepository.save(position1);

        Position position2 = new Position();
        position2.setName("Financial Analyst");
        position2.setDetails("Looking for a financial analyst with 3+ years experience.");
        position2.setOpen(true);
        position2.setCompany(company2);
        position2.setDateFrom(LocalDate.now());
        position2.setDateTo(LocalDate.now().plusMonths(2));
        position2 = positionRepository.save(position2);

        Position position3 = new Position();
        position3.setName("Frontend Developer");
        position3.setDetails("React developer position for our frontend team.");
        position3.setOpen(false);
        position3.setCompany(company1);
        position3.setDateFrom(LocalDate.now().minusMonths(1));
        position3.setDateTo(LocalDate.now().minusDays(1));
        position3 = positionRepository.save(position3);

        // Create sample candidates
        Candidate candidate1 = new Candidate();
        candidate1.setName("Marko");
        candidate1.setSurname("Petrovic");
        candidate1.setStatus("Active");
        candidate1.setEmail("marko.petrovic@email.com");
        candidate1.setPhone("+381 64 123 4567");
        candidate1 = candidateRepository.save(candidate1);

        Candidate candidate2 = new Candidate();
        candidate2.setName("Ana");
        candidate2.setSurname("Nikolic");
        candidate2.setStatus("Active");
        candidate2.setEmail("ana.nikolic@email.com");
        candidate2.setPhone("+381 65 987 6543");
        candidate2 = candidateRepository.save(candidate2);

        Candidate candidate3 = new Candidate();
        candidate3.setName("Stefan");
        candidate3.setSurname("Jovanovic");
        candidate3.setStatus("Inactive");
        candidate3.setEmail("stefan.jovanovic@email.com");
        candidate3.setPhone("+381 63 555 1234");
        candidate3 = candidateRepository.save(candidate3);

        // Create sample interviews
        Interview interview1 = new Interview();
        interview1.setDate(LocalDateTime.now().plusDays(7));
        interview1.setStatus("Scheduled");
        interview1.setCandidate(candidate1);
        interview1.setPosition(position1);
        interview1 = interviewRepository.save(interview1);

        Interview interview2 = new Interview();
        interview2.setDate(LocalDateTime.now().plusDays(10));
        interview2.setStatus("Scheduled");
        interview2.setCandidate(candidate2);
        interview2.setPosition(position2);
        interview2 = interviewRepository.save(interview2);

        System.out.println("Sample data initialized successfully!");
    }
}

