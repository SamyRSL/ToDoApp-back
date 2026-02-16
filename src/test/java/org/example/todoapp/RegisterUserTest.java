package org.example.todoapp;

import jakarta.transaction.Transactional;
import org.example.todoapp.model.CustomUserDetails;
import org.example.todoapp.repository.RefreshTokenRepository;
import org.example.todoapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RegisterUserTest extends BaseIntegrationTest {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        refreshTokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void registerUserTest() throws Exception {
        String registerPayload = """
                        {
                            "username": "user_test",
                            "password": "password_test"
                        }
                """;
        mockMvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON).content(registerPayload)).andExpect(status().isOk());
        CustomUserDetails user = userRepository.findByUsername("user_test").orElseThrow();

        assertEquals("user_test", user.getUsername());
        assertNotNull(user.getPassword());
        assertNotEquals("password_test", user.getPassword());
    }
}
