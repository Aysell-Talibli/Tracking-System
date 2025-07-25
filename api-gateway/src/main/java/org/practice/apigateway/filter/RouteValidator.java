package org.practice.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class RouteValidator {

    private final List<String> openApiEndpoints=List.of(
            "/user-service/auth/login",
            "/user-service/auth/register",
            "/auth",
            "/v3/api-docs",
            "/swagger-ui",
            "/swagger-ui.html",
            "/swagger-resources",
            "/webjars");

    public boolean isSecured(ServerHttpRequest request){
        return openApiEndpoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
    }
}
