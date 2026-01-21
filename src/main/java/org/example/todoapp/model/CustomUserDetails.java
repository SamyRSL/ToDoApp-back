package org.example.todoapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class CustomUserDetails implements UserDetails {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(unique = true, nullable = false)
    private String username;

    @Getter
    @Setter
    @Column(nullable = false)
    private String password;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }

    public record LoginRequestDTO(@NotBlank String username, @NotBlank String password) {
    }

    public record TokenDTO(String accessToken) {
    }

    public record LoginResponse(String accessToken, String refreshToken) {
    }

    public record RegisterRequestDTO(String username, String password) {
    }

    public record LogoutRequest(String refreshToken) {
    }
}

