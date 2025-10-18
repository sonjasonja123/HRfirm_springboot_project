package com.hr.repository;

import com.hr.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    
    List<Candidate> findByStatus(String status);
    
    @Query("SELECT c FROM Candidate c WHERE c.name LIKE %:name% OR c.surname LIKE %:name%")
    List<Candidate> findByNameContaining(@Param("name") String name);
    
    @Query("SELECT c FROM Candidate c WHERE c.status = :status AND (c.name LIKE %:name% OR c.surname LIKE %:name%)")
    List<Candidate> findByStatusAndNameContaining(@Param("status") String status, @Param("name") String name);
    
    @Query("SELECT c FROM Candidate c WHERE c.email = :email")
    Optional<Candidate> findByEmail(@Param("email") String email);
    
    @Query("SELECT c FROM Candidate c WHERE c.phone = :phone")
    Optional<Candidate> findByPhone(@Param("phone") String phone);
}