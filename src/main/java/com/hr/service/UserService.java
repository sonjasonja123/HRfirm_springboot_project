package com.hr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.hr.model.User;
import com.hr.model.Position;
import com.hr.repository.UserRepository;
import com.hr.repository.PositionRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> login(String username, String password) {
        return userRepository.findByUsername(username)
            .filter(u -> passwordEncoder.matches(password, u.getPassword()));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Position> getAllPositions(PositionRepository positionRepository) {
        return positionRepository.findAll();
    }
}
