package com.hr.controller;

import com.hr.model.User;
import com.hr.model.Position;
import com.hr.service.UserService;
import com.hr.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PositionRepository positionRepository;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String,String> body){
        return userService.login(body.get("username"), body.get("password"))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @GetMapping("/positions")
    public List<Position> getPositions(){
        return userService.getAllPositions(positionRepository);
    }
}
