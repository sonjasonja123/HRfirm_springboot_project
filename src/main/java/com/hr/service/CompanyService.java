package com.hr.service;

import com.hr.model.Company;
import com.hr.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyService {
    
    @Autowired
    private CompanyRepository companyRepository;
    
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
}
