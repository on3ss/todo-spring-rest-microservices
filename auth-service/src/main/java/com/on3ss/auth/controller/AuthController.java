package com.on3ss.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.on3ss.auth.dto.AuthRequest;
// import com.on3ss.auth.dto.AuthResponse;
import com.on3ss.auth.dto.RegisterRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // Logic to save user
        return ResponseEntity.ok("User registered successfully");
    }

    // @PostMapping("/login")
    // public ResponseEntity<?> login(@RequestBody AuthRequest request) {
    //     // Logic to return JWT
    //     return ResponseEntity.ok(new AuthResponse(token));
    // }
}