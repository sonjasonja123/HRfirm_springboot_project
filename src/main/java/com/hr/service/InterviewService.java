package com.hr.service;

import com.hr.model.Interview;
import com.hr.model.Candidate;
import com.hr.model.Position;
import com.hr.repository.InterviewRepository;
import com.hr.repository.CandidateRepository;
import com.hr.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InterviewService {
    
    @Autowired
    private InterviewRepository interviewRepository;
    
    @Autowired
    private CandidateRepository candidateRepository;
    
    @Autowired
    private PositionRepository positionRepository;
    
    public List<Interview> getAllInterviews() {
        return interviewRepository.findAll();
    }
    
    public Optional<Interview> getInterviewById(Long id) {
        return interviewRepository.findById(id);
    }
    
    public Interview createInterview(Interview interview) {
        return interviewRepository.save(interview);
    }
    
    public Interview createInterviewForCandidateAndPosition(Long candidateId, Long positionId, LocalDateTime date) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found with id: " + candidateId));
        
        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new RuntimeException("Position not found with id: " + positionId));
        
        Interview interview = new Interview();
        interview.setDate(date);
        interview.setStatus("Scheduled");
        interview.setCandidate(candidate);
        interview.setPosition(position);
        
        return interviewRepository.save(interview);
    }
    
    public Interview updateInterview(Long id, Interview interviewDetails) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found with id: " + id));
        
        interview.setDate(interviewDetails.getDate());
        interview.setStatus(interviewDetails.getStatus());
        
        return interviewRepository.save(interview);
    }
    
    public void deleteInterview(Long id) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found with id: " + id));
        interviewRepository.delete(interview);
    }
    
    public List<Interview> getInterviewsByStatus(String status) {
        return interviewRepository.findByStatus(status);
    }
    
    public List<Interview> getInterviewsByCandidate(Long candidateId) {
        return interviewRepository.findByCandidateId(candidateId);
    }
    
    public List<Interview> getInterviewsByPosition(Long positionId) {
        return interviewRepository.findByPositionId(positionId);
    }
    
    public List<Interview> getInterviewsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return interviewRepository.findByDateBetween(startDate, endDate);
    }
    
    public List<Interview> getInterviewsByCandidateAndPosition(Long candidateId, Long positionId) {
        return interviewRepository.findByCandidateIdAndPositionId(candidateId, positionId);
    }
    
    public List<Interview> searchInterviewsByCandidateName(String candidateName) {
        return interviewRepository.findByCandidateNameContaining(candidateName);
    }
    
    public List<Interview> searchInterviewsByPositionName(String positionName) {
        return interviewRepository.findByPositionNameContaining(positionName);
    }
    
    public Interview updateInterviewStatus(Long id, String status) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found with id: " + id));
        interview.setStatus(status);
        return interviewRepository.save(interview);
    }
}