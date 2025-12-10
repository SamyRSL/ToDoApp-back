package org.example.todoapp.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey jwtKey;

    public JwtService(SecretKey jwtKey) {
        this.jwtKey = jwtKey;
    }

    public String generateToken(String username) {
        return Jwts.builder().subject(username).issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() + 600000)).signWith(jwtKey).compact();
    }

    public String extractUsername(String token) {
        try {
            return Jwts.parser().verifyWith(jwtKey).build().parseSignedClaims(token).getPayload().getSubject();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Jeton expiré");
        }
    }
}
