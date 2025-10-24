package com.hr.service;

import com.hr.model.Interview;
import com.hr.model.InterviewRound;
import com.hr.model.InterviewType;
import com.hr.model.Candidate;
import com.hr.model.Position;
import com.hr.repository.InterviewRepository;
import com.hr.repository.InterviewRoundRepository;
import com.hr.repository.CandidateRepository;
import com.hr.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InterviewService {
    
    @Autowired
    private InterviewRepository interviewRepository;
    
    @Autowired
    private InterviewRoundRepository interviewRoundRepository;
    
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
    
    public Interview addInterviewRound(Long interviewId, String typeStr, Integer number) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found with id: " + interviewId));
        
        InterviewType type = InterviewType.valueOf(typeStr.toUpperCase());
        
        InterviewRound round = new InterviewRound();
        round.setType(type);
        round.setNumber(number);
        round.setInterview(interview);
        
        interviewRoundRepository.save(round);
        interview.addInterviewRound(round);
        
        return interviewRepository.save(interview);
    }
    
    public Interview hireCandidate(Long interviewId) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found with id: " + interviewId));
        
        // Postavi status intervjua na "Completed"
        interview.setStatus("Completed");
        
        // Zatvori poziciju
        Position position = interview.getPosition();
        position.setOpen(false);
        positionRepository.save(position);
        
        // Ažuriraj status kandidata na "Hired"
        Candidate candidate = interview.getCandidate();
        candidate.setStatus("Hired");
        candidateRepository.save(candidate);
        
        // NOVO: Automatski odbij sve ostale kandidate za istu poziciju
        List<Interview> allInterviewsForPosition = interviewRepository.findByPositionId(position.getIdPosition());
        for (Interview otherInterview : allInterviewsForPosition) {
            // Preskoči trenutni intervju (ovaj koji se zaposljava)
            if (!otherInterview.getIdInterview().equals(interviewId)) {
                // Postavi status intervjua na "Canceled"
                otherInterview.setStatus("Canceled");
                interviewRepository.save(otherInterview);
                
                // Postavi status kandidata na "Rejected" (samo ako već nije Hired)
                Candidate otherCandidate = otherInterview.getCandidate();
                if (otherCandidate != null && !"Hired".equals(otherCandidate.getStatus())) {
                    otherCandidate.setStatus("Rejected");
                    candidateRepository.save(otherCandidate);
                }
            }
        }
        
        return interviewRepository.save(interview);
    }
}