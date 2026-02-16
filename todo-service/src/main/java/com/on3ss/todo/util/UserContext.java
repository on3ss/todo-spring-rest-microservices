package com.on3ss.todo.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;

@Component
public class UserContext {

    public UUID getCurrentUserId() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("No request context");
        }
        HttpServletRequest request = attributes.getRequest();
        String userId = request.getHeader("X-User-Id");
        if (userId == null) {
            throw new IllegalStateException("Missing X-User-Id header");
        }
        return UUID.fromString(userId);
    }
}