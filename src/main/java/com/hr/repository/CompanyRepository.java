package com.hr.repository;

import com.hr.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByNameContaining(String name);
    List<Company> findByIndustryContaining(String industry);
    Optional<Company> findByName(String name);
    List<Company> findByIndustry(String industry);
    Optional<Company> findByUsername(String username);
}
