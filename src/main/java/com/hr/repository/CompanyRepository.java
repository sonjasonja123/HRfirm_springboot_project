package com.hr.repository;

import com.hr.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    
    Optional<Company> findByName(String name);
    
    List<Company> findByIndustry(String industry);
    
    @Query("SELECT c FROM Company c WHERE c.name LIKE %:name%")
    List<Company> findByNameContaining(@Param("name") String name);
    
    @Query("SELECT c FROM Company c WHERE c.industry LIKE %:industry%")
    List<Company> findByIndustryContaining(@Param("industry") String industry);
}
