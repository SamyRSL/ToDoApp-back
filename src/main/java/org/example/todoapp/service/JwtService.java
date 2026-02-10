package org.example.todoapp.service;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    @Value("${jwt.jwtExpirationMs}")
    private Long jwtTokenDurationMs;

    private final SecretKey jwtKey;
    private final JwtParser parser;

    public JwtService(SecretKey jwtKey) {
        this.jwtKey = jwtKey;
        this.parser = Jwts.parser().verifyWith(jwtKey).build();
    }

    public String generateToken(String username) {
        return Jwts.builder().subject(username).issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() + jwtTokenDurationMs)).signWith(jwtKey).compact();
    }

    public String extractUsername(String token) {
        return parser.parseSignedClaims(token).getPayload().getSubject();
    }

    public List<String> extractRoles(String token) {
        Object roles = parser.parseSignedClaims(token)
                .getPayload()
                .get("roles");
        if (roles instanceof List<?> list) {
            return list.stream().map(Object::toString).toList();
        }
        return List.of();
    }

    public void validate(String token) {
        parser.parseSignedClaims(token);
    }
}
