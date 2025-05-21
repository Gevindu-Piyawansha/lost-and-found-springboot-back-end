package com.ijse.springboot.controller;

import com.ijse.springboot.dto.*;
import com.ijse.springboot.entity.User;
import com.ijse.springboot.repository.UserRepository;
import com.ijse.springboot.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public String register(@RequestBody RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);
        return "User registered successfully";
    }

    @PostMapping("/signin")
    public AuthResponse login(@RequestBody AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                request.getUsername(), request.getPassword(), java.util.Collections.emptyList());

        String token = jwtUtil.generateToken(userDetails.getUsername());
        return new AuthResponse(token);
    }
}
