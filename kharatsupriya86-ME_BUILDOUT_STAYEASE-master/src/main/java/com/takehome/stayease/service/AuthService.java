package com.takehome.stayease.service;

import com.takehome.stayease.dto.LoginDto;
import com.takehome.stayease.dto.SignupDto;
import com.takehome.stayease.entity.User;
import com.takehome.stayease.enums.RoleEnum;
import com.takehome.stayease.exception.custom_exception.ConflictException;
import com.takehome.stayease.exception.custom_exception.InvalidCredException;
import com.takehome.stayease.repo.UserRepository;
import com.takehome.stayease.security.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;


    public AuthService(UserRepository userRepository, AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    public String signup(SignupDto dto) {

        log.info("Thread: {}, signup dto: {}", Thread.currentThread().getName(), dto);

        Optional<User> foundUser = userRepository.findByEmail(dto.getEmail());
        if (foundUser.isPresent())
            throw new ConflictException("Email already exists: " + dto.getEmail());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(7);

        User newUser = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .role((dto.getRole() == null) ? RoleEnum.USER : dto.getRole())
                .build();

        User savedUser = userRepository.save(newUser);

        log.info("Thread: {}, User signup successful: ID {}, EMAIL: {}", Thread.currentThread().getName(), savedUser.getId(), savedUser.getEmail());

        return jwtUtil.generateToken(savedUser.getEmail());
    }

    public String login(LoginDto dto) {

        log.info("Thread: {}, Trying login User: {}", Thread.currentThread().getName(), dto);

        try {

            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
            );

            log.info("Thread: {}, User logged in successfully: {}",Thread.currentThread().getName(), dto);
            return jwtUtil.generateToken(dto.getEmail());

        } catch (BadCredentialsException e) {
            log.info("Thread: {}, User login failed, due to Bad Credentials: {}, error: {}",Thread.currentThread().getName(), dto, e.getMessage());
            throw new InvalidCredException("Invalid email or password");
        } catch (AuthenticationException e) {
            log.info("Thread: {}, User login failed {}, error: {}",Thread.currentThread().getName(), dto, e.getMessage());
            log.error(e.getMessage());
            throw e;
        }
    }
}
