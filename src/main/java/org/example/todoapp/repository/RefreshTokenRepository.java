package org.example.todoapp.repository;

import org.example.todoapp.model.CustomUserDetails;
import org.example.todoapp.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(CustomUserDetails user);
}
