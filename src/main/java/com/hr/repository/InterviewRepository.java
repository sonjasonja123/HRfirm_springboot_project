package com.hr.repository;

import com.hr.model.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {
    
    List<Interview> findByStatus(String status);
    
    @Query("SELECT i FROM Interview i WHERE i.candidate.idCandidate = :candidateId")
    List<Interview> findByCandidateId(@Param("candidateId") Long candidateId);
    
    @Query("SELECT i FROM Interview i WHERE i.position.idPosition = :positionId")
    List<Interview> findByPositionId(@Param("positionId") Long positionId);
    
    @Query("SELECT i FROM Interview i WHERE i.date BETWEEN :startDate AND :endDate")
    List<Interview> findByDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT i FROM Interview i WHERE i.candidate.idCandidate = :candidateId AND i.position.idPosition = :positionId")
    List<Interview> findByCandidateIdAndPositionId(@Param("candidateId") Long candidateId, @Param("positionId") Long positionId);
    
    @Query("SELECT i FROM Interview i WHERE i.candidate.name LIKE %:candidateName% OR i.candidate.surname LIKE %:candidateName%")
    List<Interview> findByCandidateNameContaining(@Param("candidateName") String candidateName);
    
    @Query("SELECT i FROM Interview i WHERE i.position.name LIKE %:positionName%")
    List<Interview> findByPositionNameContaining(@Param("positionName") String positionName);
}