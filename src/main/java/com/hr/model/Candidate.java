package com.hr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "candidates")
public class Candidate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_candidate")
    private Long idCandidate;
    
    @NotBlank(message = "Surname is required")
    @Size(max = 50, message = "Surname must not exceed 50 characters")
    @Column(name = "surname", nullable = false)
    private String surname;
    
    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must not exceed 50 characters")
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotBlank(message = "Status is required")
    @Size(max = 20, message = "Status must not exceed 20 characters")
    @Column(name = "status", nullable = false)
    private String status = "Active";

    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Column(name = "email", length = 100)
    private String email;

    @Size(max = 30, message = "Phone must not exceed 30 characters")
    @Column(name = "phone", length = 30)
    private String phone;
    
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Interview> interviews = new ArrayList<>();
    
    // Constructors
    public Candidate() {}
    
    public Candidate(String surname, String name) {
        this.surname = surname;
        this.name = name;
        this.status = "Active";
    }
    
    // Getters and Setters
    public Long getIdCandidate() {
        return idCandidate;
    }
    
    public void setIdCandidate(Long idCandidate) {
        this.idCandidate = idCandidate;
    }
    
    public String getSurname() {
        return surname;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public List<Interview> getInterviews() {
        return interviews;
    }
    
    public void setInterviews(List<Interview> interviews) {
        this.interviews = interviews;
    }
    
    // Helper methods
    public void addInterview(Interview interview) {
        interviews.add(interview);
        interview.setCandidate(this);
    }
    
    public void removeInterview(Interview interview) {
        interviews.remove(interview);
        interview.setCandidate(null);
    }
    
    // Utility method
    public String getFullName() {
        return name + " " + surname;
    }
}
