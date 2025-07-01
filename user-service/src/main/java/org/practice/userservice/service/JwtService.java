package org.practice.userservice.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public interface JwtService {
    boolean validateToken(String token, UserDetails userDetails);

    String generateToken(String email);

    String createToken(Map<String, Object> claimsn, String email);

    Claims extractAllClaims(String token);

    public String extractEmail(String token);

    public Date extractExpiration(String token);
}

