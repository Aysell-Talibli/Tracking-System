package org.practice.apigateway.config;

import lombok.Data;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
@Data
public class AuthorizationConfig {
    private Map<String, List<String>> pathRoles = Map.of(
            "/update/status/**", List.of("ADMIN", "ROLE_ADMIN"),
            "/users/me", List.of("USER", "ADMIN")
    );
}