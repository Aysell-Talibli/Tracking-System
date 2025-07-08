package org.practice.apigateway.filter;

import org.practice.apigateway.config.AuthorizationConfig;
import org.practice.apigateway.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private final JwtUtil jwtUtil;
    private final RouteValidator validator;
    private final AuthorizationConfig authConfig;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    public AuthenticationFilter(JwtUtil jwtUtil, RouteValidator validator, AuthorizationConfig authConfig) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
        this.validator = validator;
        this.authConfig = authConfig;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();

            if (!validator.isSecured(request)
                    || path.contains("/v3/api-docs")
                    || path.contains("/swagger-ui")
                    || path.contains("/swagger-resources")
                    || path.contains("/webjars")
                    || path.endsWith(".html")
                    || path.contains("/auth")
                    || request.getMethod().equals(HttpMethod.OPTIONS)) {
                logger.info("Request path: {}", path);
                return chain.filter(exchange);
            }

            try {
                String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    logger.warn("Missing or invalid authorization header");
                    throw new RuntimeException("Missing or invalid authorization header");
                }
                String token = authHeader.substring(7);

                if (!jwtUtil.validate(token)) {
                    logger.error("Invalid or expired token");
                   throw new RuntimeException("Invalid or expired token");
                }

                String email = jwtUtil.extractUserEmail(token);
                List<String> roles = jwtUtil.extractRoles(token);

                if (!isAuthorized(path, roles)) {
                    logger.error("Access denied for path: {}, user roles: {}", path, roles);
                    throw new RuntimeException("Access denied, wrong permissions");
                }

                ServerHttpRequest mutatedRequest = exchange.getRequest()
                        .mutate()
                        .header("Authorization", authHeader)
                        .header("X-User-Email", email)
                        .header("X-User-Roles", String.join(",", roles))
                        .build();

                return chain.filter(exchange.mutate().request(mutatedRequest).build());

            } catch (Exception e) {
                logger.error("Authentication error for path: {} - {}", path, e.getMessage());
                throw new RuntimeException("Authentication failed ");
            }
        };
    }

    private boolean isAuthorized(String path, List<String> userRoles) {
        return authConfig.getPathRoles().entrySet().stream()
                .filter(entry -> pathMatches(path, entry.getKey()))
                .findFirst()
                .map(entry -> userRoles.stream().anyMatch(
                        role -> entry.getValue().contains(role) || entry.getValue().contains(role.replace("ROLE_", ""))))
                .orElse(true);
    }

    private boolean pathMatches(String path, String pattern) {
        if (pattern.endsWith("/**")) {
            String prefix = pattern.substring(0, pattern.length() - 3);
            return path.startsWith(prefix);
        }
        return path.equals(pattern);
    }

    public static class Config {
    }
}