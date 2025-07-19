package com.takehome.stayease.controller;

import com.takehome.stayease.dto.LoginDto;
import com.takehome.stayease.dto.SignupDto;
import com.takehome.stayease.entity.User;
import com.takehome.stayease.exception.custom_exception.BadPasswordException;
import com.takehome.stayease.service.AuthService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class AuthController {


    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupDto dto) {

        boolean validatePassword = validatePassword(dto.getPassword());

        if(!validatePassword) throw new BadPasswordException("Invalid password");

        String token = authService.signup(dto);
        return ResponseEntity.status(200).body(Map.of("token", token));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto dto) {
        String token = authService.login(dto);
        return ResponseEntity.status(200).body(Map.of("token", token));
    }

    private boolean validatePassword(String password) {
        if (password == null || password.length() < 8) return false;
        return password.matches(".*[A-Z].*") &&   // at least one uppercase
                password.matches(".*\\d.*") &&     // at least one digit
                password.matches(".*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/\\\\|~`].*"); // special char

    }
}

