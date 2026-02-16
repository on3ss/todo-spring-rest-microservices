package com.on3ss.todo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.on3ss.todo.dto.CreateTodoRequest;
import com.on3ss.todo.dto.TodoResponse;
import com.on3ss.todo.entity.Todo;
import com.on3ss.todo.service.TodoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/api/todos")
@AllArgsConstructor
public class TodoController {

    private final TodoService service;

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody CreateTodoRequest request) {
        Todo todo = service.createTodo(request);
        TodoResponse todoResponse = TodoResponse.builder()
                .title(todo.getTitle())
                .description(todo.getDescription())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(todoResponse);
    }

}
