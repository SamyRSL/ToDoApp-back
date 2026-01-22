package org.example.todoapp.service;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {

    @Value("${jwt.jwtExpirationMs}")
    private Long jwtTokenDurationMs;

    private final SecretKey jwtKey;

    public JwtService(SecretKey jwtKey) {
        this.jwtKey = jwtKey;
    }

    public String generateToken(String username) {
        return Jwts.builder().subject(username).issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() + jwtTokenDurationMs)).signWith(jwtKey).compact();
    }

    public Optional<String> extractUsername(String token) {
        if (token == null || token.isBlank()) {
            return Optional.empty();
        }

        if (token.chars().filter(c -> c == '.').count() != 2) {
            return Optional.empty();
        }

        return Jwts.parser().verifyWith(jwtKey).build().parseSignedClaims(token).getPayload().getSubject().describeConstable();
    }
}
