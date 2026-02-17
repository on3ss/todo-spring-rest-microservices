package com.on3ss.todo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.on3ss.todo.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, UUID> {
    public List<Todo> findByUserUuid(UUID userUuid);
}
