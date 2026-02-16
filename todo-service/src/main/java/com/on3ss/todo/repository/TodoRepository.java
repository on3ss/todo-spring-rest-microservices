package com.on3ss.todo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.on3ss.todo.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, UUID> {
    
}
