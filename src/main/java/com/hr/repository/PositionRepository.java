package com.hr.repository;

import com.hr.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    
    List<Position> findByOpenTrue();
    
    List<Position> findByOpenFalse();
    
    @Query("SELECT p FROM Position p WHERE p.company.idCompany = :companyId")
    List<Position> findByCompanyId(@Param("companyId") Long companyId);
    
    @Query("SELECT p FROM Position p WHERE p.name LIKE %:name%")
    List<Position> findByNameContaining(@Param("name") String name);
    
    @Query("SELECT p FROM Position p WHERE p.company.idCompany = :companyId AND p.open = true")
    List<Position> findOpenPositionsByCompany(@Param("companyId") Long companyId);
    
    @Query("SELECT p FROM Position p WHERE p.company.name LIKE %:companyName%")
    List<Position> findByCompanyNameContaining(@Param("companyName") String companyName);
}
