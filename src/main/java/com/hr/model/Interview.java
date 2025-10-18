package com.hr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "interviews")
public class Interview {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_interview")
    private Long idInterview;
    
    @NotNull(message = "Interview date is required")
    @Column(name = "date", nullable = false)
    private LocalDateTime date;
    
    @NotBlank(message = "Interview status is required")
    @Size(max = 20, message = "Status must not exceed 20 characters")
    @Column(name = "status", nullable = false)
    private String status = "Scheduled";
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_candidate", nullable = false)
    @JsonIgnoreProperties({"interviews", "hibernateLazyInitializer", "handler"})
    private Candidate candidate;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_position", nullable = false)
    @JsonIgnoreProperties({"interviews", "company", "hibernateLazyInitializer", "handler"})
    private Position position;
    
    @OneToMany(mappedBy = "interview", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<InterviewRound> interviewRounds = new ArrayList<>();
    
    // Constructors
    public Interview() {}
    
    public Interview(LocalDateTime date, Candidate candidate, Position position) {
        this.date = date;
        this.candidate = candidate;
        this.position = position;
        this.status = "Scheduled";
    }
    
    // Getters and Setters
    public Long getIdInterview() {
        return idInterview;
    }
    
    public void setIdInterview(Long idInterview) {
        this.idInterview = idInterview;
    }
    
    public LocalDateTime getDate() {
        return date;
    }
    
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Candidate getCandidate() {
        return candidate;
    }
    
    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
    
    public Position getPosition() {
        return position;
    }
    
    public void setPosition(Position position) {
        this.position = position;
    }
    
    public List<InterviewRound> getInterviewRounds() {
        return interviewRounds;
    }
    
    public void setInterviewRounds(List<InterviewRound> interviewRounds) {
        this.interviewRounds = interviewRounds;
    }
    
    // Helper methods
    public void addInterviewRound(InterviewRound interviewRound) {
        interviewRounds.add(interviewRound);
        interviewRound.setInterview(this);
    }
    
    public void removeInterviewRound(InterviewRound interviewRound) {
        interviewRounds.remove(interviewRound);
        interviewRound.setInterview(null);
    }
}
