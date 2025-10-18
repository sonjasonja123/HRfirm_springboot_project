package com.hr.repository;

import com.hr.model.InterviewRound;
import com.hr.model.InterviewType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewRoundRepository extends JpaRepository<InterviewRound, Long> {
    
    @Query("SELECT ir FROM InterviewRound ir WHERE ir.interview.idInterview = :interviewId")
    List<InterviewRound> findByInterviewId(@Param("interviewId") Long interviewId);
    
    @Query("SELECT ir FROM InterviewRound ir WHERE ir.type = :type")
    List<InterviewRound> findByType(@Param("type") InterviewType type);
    
    @Query("SELECT ir FROM InterviewRound ir WHERE ir.interview.idInterview = :interviewId ORDER BY ir.number")
    List<InterviewRound> findByInterviewIdOrderByNumber(@Param("interviewId") Long interviewId);
    
    @Query("SELECT ir FROM InterviewRound ir WHERE ir.interview.idInterview = :interviewId AND ir.number = :number")
    List<InterviewRound> findByInterviewIdAndNumber(@Param("interviewId") Long interviewId, @Param("number") Integer number);
}