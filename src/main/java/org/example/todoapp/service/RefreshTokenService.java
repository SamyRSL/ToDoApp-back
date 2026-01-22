package org.example.todoapp.service;

import lombok.extern.slf4j.Slf4j;
import org.example.todoapp.exception.ExpiredRefreshToken;
import org.example.todoapp.model.CustomUserDetails;
import org.example.todoapp.model.RefreshToken;
import org.example.todoapp.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class RefreshTokenService {

    @Value("${jwt.refreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken generate(CustomUserDetails user) {
        RefreshToken token = new RefreshToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpirationDate(Instant.now().plusMillis(refreshTokenDurationMs));
        return refreshTokenRepository.save(token);
    }

    public RefreshToken verify(CustomUserDetails.TokenDTO token) {
        log.info("Verifying{}", token);
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByToken(token.token());

        if (refreshToken.isEmpty()) {
            log.info("Token not found in database");
            return null;
        }

        if (refreshToken.get().getExpirationDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken.get());
            log.info("An expired refresh token has been deleted");
            return null;
        }

        return refreshToken.get();
    }

    public void revoke(CustomUserDetails user) {
        refreshTokenRepository.deleteByUser(user);
    }

    public void revokeByToken(String token) {
        refreshTokenRepository.findByToken(token).ifPresent(refreshTokenRepository::delete);
    }
}
