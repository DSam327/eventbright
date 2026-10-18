package com.workable.user_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "mysecretkeymysecretkeymysecretkey"; // must be long
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // ✅ Generate Token
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(key) // NEW way
                .compact();
    }

    // ✅ Extract Username
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // ✅ Validate Token
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // 🔑 Internal method (NEW parser API)
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key) // NEW instead of setSigningKey
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}