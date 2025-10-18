package com.hr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "interview_rounds")
public class InterviewRound {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_interview_round")
    private Long idInterviewRound;
    
    @NotNull(message = "Interview type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private InterviewType type;
    
    @NotNull(message = "Round number is required")
    @Column(name = "number", nullable = false)
    private Integer number;

    @Size(max = 1000, message = "Details must not exceed 1000 characters")
    @Column(name = "details", length = 1000)
    private String details;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_interview", nullable = false)
    @JsonIgnore
    private Interview interview;
    
    // Constructors
    public InterviewRound() {}
    
    public InterviewRound(InterviewType type, Integer number, Interview interview) {
        this.type = type;
        this.number = number;
        this.interview = interview;
    }
    
    // Getters and Setters
    public Long getIdInterviewRound() {
        return idInterviewRound;
    }
    
    public void setIdInterviewRound(Long idInterviewRound) {
        this.idInterviewRound = idInterviewRound;
    }
    
    public InterviewType getType() {
        return type;
    }
    
    public void setType(InterviewType type) {
        this.type = type;
    }
    
    public Integer getNumber() {
        return number;
    }
    
    public void setNumber(Integer number) {
        this.number = number;
    }


    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    
    public Interview getInterview() {
        return interview;
    }
    
    public void setInterview(Interview interview) {
        this.interview = interview;
    }
}
