package com.ijse.springboot.controller;

import com.ijse.springboot.dto.AuthRequest;
import com.ijse.springboot.dto.JwtResponse;
import com.ijse.springboot.dto.RegisterRequest;
import com.ijse.springboot.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signin(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.authenticateUser(authRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest request) {
        authService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }
}
