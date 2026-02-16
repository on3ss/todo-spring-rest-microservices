package com.on3ss.todo.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TodoResponse {
    private UUID uuid;
    private String title;
    private String description;
}
