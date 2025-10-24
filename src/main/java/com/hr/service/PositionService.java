package com.hr.service;

import com.hr.model.Company;
import com.hr.model.Position;
import com.hr.repository.PositionRepository;
import com.hr.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PositionService {
    
    @Autowired
    private PositionRepository positionRepository;
    
    @Autowired
    private CompanyService companyService;
    
    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }
    
    public List<Position> getOpenPositions() {
        return positionRepository.findByOpenTrue();
    }
    
    public List<Position> getClosedPositions() {
        return positionRepository.findByOpenFalse();
    }
    
    public Optional<Position> getPositionById(Long id) {
        return positionRepository.findById(id);
    }
    
    public Position createPosition(Position position) {
        // Automatically update status based on dates if dates are provided
        position.updateOpenStatusByDates();
        return positionRepository.save(position);
    }
    
    public Position createPositionForCompany(Long companyId, Position position) {
        Optional<Company> company = companyService.getCompanyById(companyId);
        if (company.isPresent()) {
            position.setCompany(company.get());
            // Automatically update status based on dates if dates are provided
            position.updateOpenStatusByDates();
            return positionRepository.save(position);
        }
        throw new RuntimeException("Company not found with id: " + companyId);
    }
    
    public Position updatePosition(Long id, Position positionDetails) {
        Optional<Position> optionalPosition = positionRepository.findById(id);
        if (optionalPosition.isPresent()) {
            Position position = optionalPosition.get();
            position.setName(positionDetails.getName());
            position.setDetails(positionDetails.getDetails());
            position.setOpen(positionDetails.getOpen());
            position.setDateFrom(positionDetails.getDateFrom());
            position.setDateTo(positionDetails.getDateTo());
            // Automatically update status based on dates if dates are provided
            position.updateOpenStatusByDates();
            return positionRepository.save(position);
        }
        throw new RuntimeException("Position not found with id: " + id);
    }
    
    public void deletePosition(Long id) {
        positionRepository.deleteById(id);
    }
    
    public List<Position> getPositionsByCompany(Long companyId) {
        return positionRepository.findByCompanyId(companyId);
    }
    
    public List<Position> getOpenPositionsByCompany(Long companyId) {
        return positionRepository.findOpenPositionsByCompany(companyId);
    }
    
    public List<Position> searchPositionsByName(String name) {
        return positionRepository.findByNameContaining(name);
    }
    
    public List<Position> searchPositionsByCompanyName(String companyName) {
        return positionRepository.findByCompanyNameContaining(companyName);
    }
    
    public Position closePosition(Long id) {
        Optional<Position> optionalPosition = positionRepository.findById(id);
        if (optionalPosition.isPresent()) {
            Position position = optionalPosition.get();
            position.setOpen(false);
            return positionRepository.save(position);
        }
        throw new RuntimeException("Position not found with id: " + id);
    }
    
    public Position openPosition(Long id) {
        Optional<Position> optionalPosition = positionRepository.findById(id);
        if (optionalPosition.isPresent()) {
            Position position = optionalPosition.get();
            position.setOpen(true);
            return positionRepository.save(position);
        }
        throw new RuntimeException("Position not found with id: " + id);
    }
    
    public void updateAllPositionStatusesByDates() {
        List<Position> allPositions = positionRepository.findAll();
        for (Position position : allPositions) {
            position.updateOpenStatusByDates();
            positionRepository.save(position);
        }
    }
}
