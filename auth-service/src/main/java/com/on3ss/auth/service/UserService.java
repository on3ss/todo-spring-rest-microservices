package com.on3ss.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.on3ss.auth.domain.User;
import com.on3ss.auth.dto.RegisterRequest;
import com.on3ss.auth.repository.UserRepository;
import com.on3ss.auth.utils.JwtProvider;
import com.on3ss.common.exceptions.BusinessException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public String register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return jwtProvider.generateToken(user);
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Invalid email or password."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException("Invalid email or password.");
        }

        return jwtProvider.generateToken(user);
    }
}