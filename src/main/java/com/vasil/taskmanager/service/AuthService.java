package com.vasil.taskmanager.service;

import com.vasil.taskmanager.config.JwtService;
import com.vasil.taskmanager.dto.AuthRequest;
import com.vasil.taskmanager.dto.AuthResponse;
import com.vasil.taskmanager.dto.RegisterRequest;
import com.vasil.taskmanager.entity.User;
import com.vasil.taskmanager.exception.AppException;
import com.vasil.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest req) {
        // Check if email already exists
        if (userRepository.existsByEmail(req.getEmail())) {
            System.out.println("Email already registered");
            throw new AppException("Email already registered", HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role("USER")
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new AppException("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}
