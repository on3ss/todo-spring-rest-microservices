package com.on3ss.todo.service;

import org.springframework.stereotype.Service;

import com.on3ss.todo.dto.CreateTodoRequest;
import com.on3ss.todo.entity.Todo;
import com.on3ss.todo.repository.TodoRepository;
import com.on3ss.todo.util.UserContext;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TodoService {
    private final TodoRepository repository;
    private final UserContext userContext;

    public Todo createTodo(CreateTodoRequest request) {
        Todo todo = Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .userId(userContext.getCurrentUserId())
                .build();

        return repository.save(todo);
    }
}
