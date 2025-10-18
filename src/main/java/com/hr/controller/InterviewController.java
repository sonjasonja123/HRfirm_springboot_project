package com.hr.controller;

import com.hr.model.Interview;
import com.hr.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/interviews")
@CrossOrigin(origins = "http://localhost:3000")
public class InterviewController {
    
    @Autowired
    private InterviewService interviewService;
    
    @GetMapping
    public ResponseEntity<List<Interview>> getAllInterviews() {
        List<Interview> interviews = interviewService.getAllInterviews();
        return ResponseEntity.ok(interviews);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Interview> getInterviewById(@PathVariable Long id) {
        Optional<Interview> interview = interviewService.getInterviewById(id);
        return interview.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Interview> createInterview(@Valid @RequestBody Interview interview) {
        Interview createdInterview = interviewService.createInterview(interview);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInterview);
    }
    
    @PostMapping("/candidate/{candidateId}/position/{positionId}")
    public ResponseEntity<Interview> createInterviewForCandidateAndPosition(
            @PathVariable Long candidateId, 
            @PathVariable Long positionId, 
            @RequestParam String date) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(date);
            Interview interview = interviewService.createInterviewForCandidateAndPosition(candidateId, positionId, dateTime);
            return ResponseEntity.status(HttpStatus.CREATED).body(interview);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Interview> updateInterview(@PathVariable Long id, @Valid @RequestBody Interview interviewDetails) {
        try {
            Interview updatedInterview = interviewService.updateInterview(id, interviewDetails);
            return ResponseEntity.ok(updatedInterview);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterview(@PathVariable Long id) {
        try {
            interviewService.deleteInterview(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Interview>> getInterviewsByStatus(@PathVariable String status) {
        List<Interview> interviews = interviewService.getInterviewsByStatus(status);
        return ResponseEntity.ok(interviews);
    }
    
    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<Interview>> getInterviewsByCandidate(@PathVariable Long candidateId) {
        List<Interview> interviews = interviewService.getInterviewsByCandidate(candidateId);
        return ResponseEntity.ok(interviews);
    }
    
    @GetMapping("/position/{positionId}")
    public ResponseEntity<List<Interview>> getInterviewsByPosition(@PathVariable Long positionId) {
        List<Interview> interviews = interviewService.getInterviewsByPosition(positionId);
        return ResponseEntity.ok(interviews);
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<Interview>> getInterviewsByDateRange(
            @RequestParam String startDate, 
            @RequestParam String endDate) {
        try {
            LocalDateTime start = LocalDateTime.parse(startDate);
            LocalDateTime end = LocalDateTime.parse(endDate);
            List<Interview> interviews = interviewService.getInterviewsByDateRange(start, end);
            return ResponseEntity.ok(interviews);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/candidate/{candidateId}/position/{positionId}")
    public ResponseEntity<List<Interview>> getInterviewsByCandidateAndPosition(
            @PathVariable Long candidateId, 
            @PathVariable Long positionId) {
        List<Interview> interviews = interviewService.getInterviewsByCandidateAndPosition(candidateId, positionId);
        return ResponseEntity.ok(interviews);
    }
    
    @GetMapping("/search/candidate")
    public ResponseEntity<List<Interview>> searchInterviewsByCandidateName(@RequestParam String candidateName) {
        List<Interview> interviews = interviewService.searchInterviewsByCandidateName(candidateName);
        return ResponseEntity.ok(interviews);
    }
    
    @GetMapping("/search/position")
    public ResponseEntity<List<Interview>> searchInterviewsByPositionName(@RequestParam String positionName) {
        List<Interview> interviews = interviewService.searchInterviewsByPositionName(positionName);
        return ResponseEntity.ok(interviews);
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<Interview> updateInterviewStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            Interview interview = interviewService.updateInterviewStatus(id, status);
            return ResponseEntity.ok(interview);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}