package org.practice.apigateway.filter;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.practice.apigateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import java.util.List;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private final JwtUtil jwtUtil;
    private final RouteValidator validator;

    public AuthenticationFilter(JwtUtil jwtUtil, RouteValidator validator) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
        this.validator = validator;
    }

    @Override
    public GatewayFilter apply(Config config) {
       return (exchange, chain) -> {
           ServerHttpRequest request= exchange.getRequest();
           String path=request.getURI().getPath();

           if(!validator.isSecured(request)
                   || path.contains("/v3/api-docs")
                   || path.contains("/swagger-ui")
                   || path.contains("/swagger-resources")
                   || path.contains("/webjars")
                   || path.endsWith(".html")
                   || path.contains("/auth")
                   || request.getMethod().equals(HttpMethod.OPTIONS)){

               return chain.filter(exchange);
           }
//           if(request.getURI().getPath().contains("/auth") ||
//                   request.getMethod().equals(HttpMethod.OPTIONS)){
//               return chain.filter(exchange);
//           }
           String authHeader=request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
           if(authHeader == null || !authHeader.startsWith("Bearer ")){
               throw new RuntimeException("Missing or invalid header");
           }
           String token=authHeader.substring(7);
           String email= jwtUtil.extractUserEmail(token);
           List<String> roles=jwtUtil.extractRoles(token);
           System.out.println("user roles: "+roles);

           if (path.startsWith("/user-service/users/me") && !roles.contains("USER")) {
               throw new RuntimeException("Access denied: USER role required");
           }

           if (path.startsWith("/user-service/admin/users") && !roles.contains("ADMIN")) {
               throw new RuntimeException("Access denied: ADMIN role required");
           }
           ServerHttpRequest mutatedRequest = exchange.getRequest()
                   .mutate()
                   .header("Authorization", authHeader)
                   .header("X-User-Email", email)
                   .build();

           return chain.filter(exchange.mutate().request(mutatedRequest).build());
       };

    }

    public static class Config {
    }

}