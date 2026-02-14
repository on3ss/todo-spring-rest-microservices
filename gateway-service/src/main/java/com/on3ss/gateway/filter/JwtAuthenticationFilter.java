package com.on3ss.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // 1. Check if the path should be public (e.g., /api/auth/register)
            String path = exchange.getRequest().getURI().getPath();
            if (path.contains("/api/auth/")) {
                return chain.filter(exchange);
            }

            // 2. Validate Token (Logic to be implemented based on your JwtProvider)
            // If valid, extract userId and add to Header:
            exchange.getRequest().mutate()
                    .header("X-User-Id", "extracted-id-here")
                    .build();

            return chain.filter(exchange);
        };
    }

    public static class Config {
    }
}