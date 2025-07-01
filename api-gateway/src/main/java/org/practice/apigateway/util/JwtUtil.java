package org.practice.apigateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.List;

@Component
public class JwtUtil {
    private final Key key= Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public boolean validate(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }
        catch(JwtException | IllegalArgumentException e){
            return false;
        }
    }

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String extractUserEmail(String token){
        return extractAllClaims(token).get("email", String.class);
    }

    public List<String> extractRoles(String token){
        return extractAllClaims(token).get("roles", List.class);
    }
}
