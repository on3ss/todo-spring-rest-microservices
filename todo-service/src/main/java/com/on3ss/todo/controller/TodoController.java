package com.on3ss.todo.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.on3ss.todo.dto.CreateTodoRequest;
import com.on3ss.todo.dto.TodoResponse;
import com.on3ss.todo.dto.ToggleTodoRequest;
import com.on3ss.todo.entity.Todo;
import com.on3ss.todo.service.TodoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/api/todos")
@AllArgsConstructor
public class TodoController {

    private final TodoService service;

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody CreateTodoRequest request) {
        Todo todo = service.createTodo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(TodoResponse.fromEntity(todo));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> todos() {
        List<Todo> todos = service.todos();
        List<TodoResponse> todoResponses = todos.stream()
                .map(TodoResponse::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(todoResponses);
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<TodoResponse> toggle(@PathVariable UUID uuid, @RequestBody ToggleTodoRequest request) {
        Todo todo = service.toggle(uuid, request.isCompleted());
        return ResponseEntity.ok(TodoResponse.fromEntity(todo));
    }

}
