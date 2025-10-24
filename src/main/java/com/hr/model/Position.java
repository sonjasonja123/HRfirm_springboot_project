package com.hr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "positions")
public class Position {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_position")
    private Long idPosition;
    
    @NotBlank(message = "Position name is required")
    @Size(max = 100, message = "Position name must not exceed 100 characters")
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotBlank(message = "Position details are required")
    @Size(max = 1000, message = "Position details must not exceed 1000 characters")
    @Column(name = "details", nullable = false, length = 1000)
    private String details;
    
    @Column(name = "is_open", nullable = false)
    private Boolean open = true;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_company", nullable = false)
    @JsonIgnoreProperties({"positions", "username", "password"})
    private Company company;
    
    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Interview> interviews = new ArrayList<>();

    @Column(name = "date_from")
    private LocalDate dateFrom;

    @Column(name = "date_to")
    private LocalDate dateTo;
    
    // Constructors
    public Position() {}
    
    public Position(String name, String details, Company company) {
        this.name = name;
        this.details = details;
        this.company = company;
        this.open = true;
    }
    
    // Getters and Setters
    public Long getIdPosition() {
        return idPosition;
    }
    
    public void setIdPosition(Long idPosition) {
        this.idPosition = idPosition;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDetails() {
        return details;
    }
    
    public void setDetails(String details) {
        this.details = details;
    }
    
    public Boolean getOpen() {
        return open;
    }
    
    public void setOpen(Boolean open) {
        this.open = open;
    }
    
    public Company getCompany() {
        return company;
    }
    
    public void setCompany(Company company) {
        this.company = company;
    }
    
    public List<Interview> getInterviews() {
        return interviews;
    }
    
    public void setInterviews(List<Interview> interviews) {
        this.interviews = interviews;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }
    
    // Helper methods
    public void addInterview(Interview interview) {
        interviews.add(interview);
        interview.setPosition(this);
    }
    
    public void removeInterview(Interview interview) {
        interviews.remove(interview);
        interview.setPosition(null);
    }
    
    // Method to check if position should be open based on dates
    public boolean isOpenByDate() {
        if (dateFrom == null || dateTo == null) {
            return open; // If no dates set, use manual open status
        }
        LocalDate now = LocalDate.now();
        return !now.isBefore(dateFrom) && !now.isAfter(dateTo);
    }
    
    // Method to automatically update open status based on dates
    public void updateOpenStatusByDates() {
        if (dateFrom != null && dateTo != null) {
            this.open = isOpenByDate();
        }
    }
}
