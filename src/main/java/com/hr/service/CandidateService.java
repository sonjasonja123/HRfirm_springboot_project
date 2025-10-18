package com.hr.service;

import com.hr.model.Candidate;
import com.hr.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {
    
    @Autowired
    private CandidateRepository candidateRepository;
    
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }
    
    public Optional<Candidate> getCandidateById(Long id) {
        return candidateRepository.findById(id);
    }
    
    public Candidate createCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }
    
    public Candidate updateCandidate(Long id, Candidate candidateDetails) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found with id: " + id));
        
        candidate.setName(candidateDetails.getName());
        candidate.setSurname(candidateDetails.getSurname());
        candidate.setStatus(candidateDetails.getStatus());
        candidate.setEmail(candidateDetails.getEmail());
        candidate.setPhone(candidateDetails.getPhone());
        
        return candidateRepository.save(candidate);
    }
    
    public void deleteCandidate(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found with id: " + id));
        candidateRepository.delete(candidate);
    }
    
    public List<Candidate> getCandidatesByStatus(String status) {
        return candidateRepository.findByStatus(status);
    }
    
    public List<Candidate> searchCandidatesByName(String name) {
        return candidateRepository.findByNameContaining(name);
    }
    
    public List<Candidate> searchCandidatesByStatusAndName(String status, String name) {
        return candidateRepository.findByStatusAndNameContaining(status, name);
    }
    
    public Candidate updateCandidateStatus(Long id, String status) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found with id: " + id));
        candidate.setStatus(status);
        return candidateRepository.save(candidate);
    }
}