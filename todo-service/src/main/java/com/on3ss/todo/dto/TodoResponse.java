package com.on3ss.todo.dto;

import java.util.UUID;

import com.on3ss.todo.entity.Todo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TodoResponse {
    private UUID uuid;
    private String title;
    private String description;
    private boolean completed;

    public static TodoResponse fromEntity(Todo todo) {
        return TodoResponse.builder()
                .uuid(todo.getUuid())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .completed(todo.isCompleted())
                .build();
    }
}
