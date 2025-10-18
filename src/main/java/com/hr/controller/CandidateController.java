package com.hr.controller;

import com.hr.model.Candidate;
import com.hr.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/candidates")
@CrossOrigin(origins = "http://localhost:3000")
public class CandidateController {
    
    @Autowired
    private CandidateService candidateService;
    
    @GetMapping
    public ResponseEntity<List<Candidate>> getAllCandidates() {
        List<Candidate> candidates = candidateService.getAllCandidates();
        return ResponseEntity.ok(candidates);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable Long id) {
        Optional<Candidate> candidate = candidateService.getCandidateById(id);
        return candidate.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Candidate> createCandidate(@Valid @RequestBody Candidate candidate) {
        Candidate createdCandidate = candidateService.createCandidate(candidate);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCandidate);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Candidate> updateCandidate(@PathVariable Long id, @Valid @RequestBody Candidate candidateDetails) {
        try {
            Candidate updatedCandidate = candidateService.updateCandidate(id, candidateDetails);
            return ResponseEntity.ok(updatedCandidate);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        try {
            candidateService.deleteCandidate(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Candidate>> getCandidatesByStatus(@PathVariable String status) {
        List<Candidate> candidates = candidateService.getCandidatesByStatus(status);
        return ResponseEntity.ok(candidates);
    }
    
    @GetMapping("/search/name")
    public ResponseEntity<List<Candidate>> searchCandidatesByName(@RequestParam String name) {
        List<Candidate> candidates = candidateService.searchCandidatesByName(name);
        return ResponseEntity.ok(candidates);
    }
    
    @GetMapping("/search/status")
    public ResponseEntity<List<Candidate>> searchCandidatesByStatusAndName(@RequestParam String status, @RequestParam String name) {
        List<Candidate> candidates = candidateService.searchCandidatesByStatusAndName(status, name);
        return ResponseEntity.ok(candidates);
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<Candidate> updateCandidateStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            Candidate candidate = candidateService.updateCandidateStatus(id, status);
            return ResponseEntity.ok(candidate);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}