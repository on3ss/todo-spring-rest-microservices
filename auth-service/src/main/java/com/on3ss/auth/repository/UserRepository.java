package com.on3ss.auth.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.on3ss.auth.domain.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);

    boolean existsByEmail(String email);
}
