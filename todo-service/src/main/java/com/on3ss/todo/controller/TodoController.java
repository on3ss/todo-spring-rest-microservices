package com.on3ss.todo.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.on3ss.todo.dto.CreateTodoRequest;
import com.on3ss.todo.dto.PageResponse;
import com.on3ss.todo.dto.TodoResponse;
import com.on3ss.todo.dto.ToggleTodoRequest;
import com.on3ss.todo.entity.Todo;
import com.on3ss.todo.mapper.PaginationMapper;
import com.on3ss.todo.service.TodoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/api/todos")
@AllArgsConstructor
public class TodoController {

    private final TodoService service;
    private final PaginationMapper paginationMapper;

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody CreateTodoRequest request) {
        Todo todo = service.createTodo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(TodoResponse.fromEntity(todo));
    }

    @GetMapping
    public ResponseEntity<PageResponse<TodoResponse>> todos(Pageable pageable) {
        Page<Todo> todoPage = service.todos(pageable);
        PageResponse<TodoResponse> response = paginationMapper.toPageResponse(todoPage, TodoResponse::fromEntity);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<TodoResponse> toggle(@PathVariable UUID uuid, @RequestBody ToggleTodoRequest request) {
        Todo todo = service.toggle(uuid, request.isCompleted());
        return ResponseEntity.ok(TodoResponse.fromEntity(todo));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        service.delete(uuid);
        return ResponseEntity.noContent().build();
    }

}
