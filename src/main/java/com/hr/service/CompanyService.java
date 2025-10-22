package com.hr.service;

import com.hr.model.Company;
import com.hr.model.Position;
import com.hr.repository.CompanyRepository;
import com.hr.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyService {
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private PositionRepository positionRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
    
    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }
    
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }
    
    public Company updateCompany(Long id, Company companyDetails) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()) {
            Company company = optionalCompany.get();
            company.setName(companyDetails.getName());
            company.setIndustry(companyDetails.getIndustry());
            return companyRepository.save(company);
        }
        throw new RuntimeException("Company not found with id: " + id);
    }
    
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
    
    public List<Company> searchCompaniesByName(String name) {
        return companyRepository.findByNameContaining(name);
    }
    
    public List<Company> searchCompaniesByIndustry(String industry) {
        return companyRepository.findByIndustryContaining(industry);
    }
    
    public Optional<Company> getCompanyByName(String name) {
        return companyRepository.findByName(name);
    }
    
    public List<Company> getCompaniesByIndustry(String industry) {
        return companyRepository.findByIndustry(industry);
    }

    // Login methods for company
    public Optional<Company> login(String username, String password) {
        return companyRepository.findByUsername(username)
                .filter(c -> passwordEncoder.matches(password, c.getPassword()));
    }

    // Position management methods
    public Position postPosition(Company company, String title, String description) {
        Position position = new Position();
        position.setName(title);
        position.setDetails(description);
        position.setCompany(company);
        return positionRepository.save(position);
    }

    public List<Position> getMyPositions(Company company) {
        return positionRepository.findByCompanyId(company.getIdCompany());
    }
    
    public Position updatePosition(Long positionId, String title, String description) {
        Optional<Position> optionalPosition = positionRepository.findById(positionId);
        if (optionalPosition.isPresent()) {
            Position position = optionalPosition.get();
            position.setName(title);
            position.setDetails(description);
            return positionRepository.save(position);
        }
        throw new RuntimeException("Position not found with id: " + positionId);
    }
}
