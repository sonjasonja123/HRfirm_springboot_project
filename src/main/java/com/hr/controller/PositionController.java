package com.hr.controller;

import com.hr.model.Position;
import com.hr.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/positions")
@CrossOrigin(origins = "http://localhost:3000")
public class PositionController {
    
    @Autowired
    private PositionService positionService;
    
    @GetMapping
    public ResponseEntity<List<Position>> getAllPositions() {
        List<Position> positions = positionService.getAllPositions();
        return ResponseEntity.ok(positions);
    }
    
    @GetMapping("/open")
    public ResponseEntity<List<Position>> getOpenPositions() {
        List<Position> positions = positionService.getOpenPositions();
        return ResponseEntity.ok(positions);
    }
    
    @GetMapping("/closed")
    public ResponseEntity<List<Position>> getClosedPositions() {
        List<Position> positions = positionService.getClosedPositions();
        return ResponseEntity.ok(positions);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Position> getPositionById(@PathVariable Long id) {
        Optional<Position> position = positionService.getPositionById(id);
        return position.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Position> createPosition(@Valid @RequestBody Position position) {
        Position createdPosition = positionService.createPosition(position);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPosition);
    }
    
    @PostMapping("/company/{companyId}")
    public ResponseEntity<Position> createPositionForCompany(@PathVariable Long companyId, @Valid @RequestBody Position position) {
        try {
            Position createdPosition = positionService.createPositionForCompany(companyId, position);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPosition);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Position> updatePosition(@PathVariable Long id, @Valid @RequestBody Position positionDetails) {
        try {
            Position updatedPosition = positionService.updatePosition(id, positionDetails);
            return ResponseEntity.ok(updatedPosition);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        try {
            positionService.deletePosition(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Position>> getPositionsByCompany(@PathVariable Long companyId) {
        List<Position> positions = positionService.getPositionsByCompany(companyId);
        return ResponseEntity.ok(positions);
    }
    
    @GetMapping("/company/{companyId}/open")
    public ResponseEntity<List<Position>> getOpenPositionsByCompany(@PathVariable Long companyId) {
        List<Position> positions = positionService.getOpenPositionsByCompany(companyId);
        return ResponseEntity.ok(positions);
    }
    
    @GetMapping("/search/name")
    public ResponseEntity<List<Position>> searchPositionsByName(@RequestParam String name) {
        List<Position> positions = positionService.searchPositionsByName(name);
        return ResponseEntity.ok(positions);
    }
    
    @GetMapping("/search/company")
    public ResponseEntity<List<Position>> searchPositionsByCompanyName(@RequestParam String companyName) {
        List<Position> positions = positionService.searchPositionsByCompanyName(companyName);
        return ResponseEntity.ok(positions);
    }
    
    @PutMapping("/{id}/close")
    public ResponseEntity<Position> closePosition(@PathVariable Long id) {
        try {
            Position position = positionService.closePosition(id);
            return ResponseEntity.ok(position);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/open")
    public ResponseEntity<Position> openPosition(@PathVariable Long id) {
        try {
            Position position = positionService.openPosition(id);
            return ResponseEntity.ok(position);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
