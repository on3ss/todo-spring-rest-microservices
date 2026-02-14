package com.on3ss.auth.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}