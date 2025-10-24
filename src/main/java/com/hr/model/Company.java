package com.hr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
public class Company {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_company")
    private Long idCompany;
    
    @NotBlank(message = "Company name is required")
    @Size(max = 100, message = "Company name must not exceed 100 characters")
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotBlank(message = "Industry is required")
    @Size(max = 100, message = "Industry must not exceed 100 characters")
    @Column(name = "industry", nullable = false)
    private String industry;

    @NotBlank(message = "PIB is required")
    @Size(max = 50, message = "PIB must not exceed 50 characters")
    @Column(name = "pib", nullable = false, length = 50)
    private String pib;

    @Size(max = 100, message = "Contact must not exceed 100 characters")
    @Column(name = "contact", length = 100)
    private String contact;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;
    
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"company", "interviews"})
    private List<Position> positions = new ArrayList<>();
    
    // Constructors
    public Company() {}
    
    public Company(String name, String industry) {
        this.name = name;
        this.industry = industry;
    }
    
    // Getters and Setters
    public Long getIdCompany() {
        return idCompany;
    }
    
    public void setIdCompany(Long idCompany) {
        this.idCompany = idCompany;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getIndustry() {
        return industry;
    }
    
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public List<Position> getPositions() {
        return positions;
    }
    
    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }
    
    // Helper methods
    public void addPosition(Position position) {
        positions.add(position);
        position.setCompany(this);
    }
    
    public void removePosition(Position position) {
        positions.remove(position);
        position.setCompany(null);
    }
}
