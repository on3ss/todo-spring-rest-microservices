package com.on3ss.todo.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.on3ss.common.exceptions.BusinessException;
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
                .userUuid(userContext.getCurrentUserId())
                .build();

        return repository.save(todo);
    }

    public Page<Todo> todos(Pageable pageable) {
        return repository.findByUserUuid(userContext.getCurrentUserId(), pageable);
    }

    public Todo toggle(UUID uuid, boolean completed) {
        Todo todo = repository.findById(uuid)
                .orElseThrow(() -> new BusinessException("Todo not found!"));

        if (!todo.getUserUuid().equals(userContext.getCurrentUserId())) {
            throw new BusinessException("You do not have permission to update this todo");
        }

        todo.setCompleted(completed);

        return repository.save(todo);
    }

    public void delete(UUID uuid){
        Todo todo = repository.findById(uuid)
                .orElseThrow(() -> new BusinessException("Todo not found!"));

        if (!todo.getUserUuid().equals(userContext.getCurrentUserId())) {
            throw new BusinessException("You do not have permission to delete this todo");
        }
        repository.delete(todo);
    }
}
