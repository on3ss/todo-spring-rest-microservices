package com.on3ss.auth.controller;

import java.util.Collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.on3ss.auth.dto.AuthRequest;
// import com.on3ss.auth.dto.AuthResponse;
import com.on3ss.auth.dto.RegisterRequest;
import com.on3ss.auth.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        String token = userService.register(request);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    // @PostMapping("/login")
    // public ResponseEntity<?> login(@RequestBody AuthRequest request) {
    // // Logic to return JWT
    // return ResponseEntity.ok(new AuthResponse(token));
    // }
}