package com.on3ss.auth.utils;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Configuration;

import com.on3ss.auth.config.JwtProperties;
import com.on3ss.auth.domain.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Configuration
public class JwtProvider {
    private final SecretKey key;

    public JwtProvider(JwtProperties properties){
        String secret = properties.getSecret();
        if(secret == null || secret.length() < 32){
            throw new IllegalArgumentException("JWT secret must be at least 32 characters long");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getUuid())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}