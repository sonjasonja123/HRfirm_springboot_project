package com.hr.controller;

import com.hr.model.Company;
import com.hr.model.Position;
import com.hr.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/company")
@CrossOrigin(origins = "http://localhost:3000")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/login")
    public ResponseEntity<Company> login(@RequestBody Map<String,String> body){
        return companyService.login(body.get("username"), body.get("password"))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/postPosition")
    public ResponseEntity<Position> postPosition(@RequestBody Map<String,String> body){
        Company company = companyService.login(body.get("username"), body.get("password"))
                .orElseThrow(() -> new RuntimeException("Invalid company"));
        
        // Parse dates if provided
        LocalDate dateFrom = body.get("dateFrom") != null && !body.get("dateFrom").isEmpty() 
            ? LocalDate.parse(body.get("dateFrom")) 
            : null;
        LocalDate dateTo = body.get("dateTo") != null && !body.get("dateTo").isEmpty() 
            ? LocalDate.parse(body.get("dateTo")) 
            : null;
        
        Position position = companyService.postPosition(company, body.get("title"), body.get("description"), dateFrom, dateTo);
        return ResponseEntity.ok(position);
    }

    @GetMapping("/positions")
    public ResponseEntity<List<Position>> getCompanyPositions(@RequestParam String username, @RequestParam String password){
        Company company = companyService.login(username, password)
                .orElseThrow(() -> new RuntimeException("Invalid company"));
        List<Position> positions = companyService.getMyPositions(company);
        return ResponseEntity.ok(positions);
    }

    @PutMapping("/positions/{id}")
    public ResponseEntity<Position> updatePosition(@PathVariable Long id, @RequestBody Map<String,String> body){
        try {
            // Parse dates if provided
            LocalDate dateFrom = body.get("dateFrom") != null && !body.get("dateFrom").isEmpty() 
                ? LocalDate.parse(body.get("dateFrom")) 
                : null;
            LocalDate dateTo = body.get("dateTo") != null && !body.get("dateTo").isEmpty() 
                ? LocalDate.parse(body.get("dateTo")) 
                : null;
            
            Position position = companyService.updatePosition(id, body.get("title"), body.get("description"), dateFrom, dateTo);
            return ResponseEntity.ok(position);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Endpoint-i za Worker (HR) pristup kompanijama
    @GetMapping("/all")
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }
}









/*
@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "http://localhost:3000")
public class CompanyController {
    
    @Autowired
    private CompanyService companyService;
    
    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Optional<Company> company = companyService.getCompanyById(id);
        return company.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Company> createCompany(@Valid @RequestBody Company company) {
        Company createdCompany = companyService.createCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @Valid @RequestBody Company companyDetails) {
        try {
            Company updatedCompany = companyService.updateCompany(id, companyDetails);
            return ResponseEntity.ok(updatedCompany);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        try {
            companyService.deleteCompany(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/search/name")
    public ResponseEntity<List<Company>> searchCompaniesByName(@RequestParam String name) {
        List<Company> companies = companyService.searchCompaniesByName(name);
        return ResponseEntity.ok(companies);
    }
    
    @GetMapping("/search/industry")
    public ResponseEntity<List<Company>> searchCompaniesByIndustry(@RequestParam String industry) {
        List<Company> companies = companyService.searchCompaniesByIndustry(industry);
        return ResponseEntity.ok(companies);
    }
    
    @GetMapping("/industry/{industry}")
    public ResponseEntity<List<Company>> getCompaniesByIndustry(@PathVariable String industry) {
        List<Company> companies = companyService.getCompaniesByIndustry(industry);
        return ResponseEntity.ok(companies);
    }
}
*/