package org.example.todoapp;

import lombok.extern.slf4j.Slf4j;
import org.example.todoapp.model.CustomUserDetails;
import org.example.todoapp.repository.RefreshTokenRepository;
import org.example.todoapp.repository.UserRepository;
import org.example.todoapp.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class JwtTest extends BaseIntegrationTest {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

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
    void authenticate() throws Exception {
        CustomUserDetails.RegisterRequestDTO registerRequestDTO = new CustomUserDetails.RegisterRequestDTO("user_test", "password_test");
        customUserDetailsService.register(registerRequestDTO);

        String loginPayload = """
                        {
                            "username": "user_test",
                            "password": "password_test"
                        }
                """;

        ObjectMapper mapper = new ObjectMapper();

        String response = mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(loginPayload)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        log.info(response);

        JsonNode rootNode = mapper.readTree(response);

        String accessToken = rootNode.get("accessToken").asText();
        String refreshToken = rootNode.get("refreshToken").asText();

        mockMvc.perform(get("/api/tasks/my").header("Authorization", "Bearer " + accessToken)).andExpect(status().isOk());

        String refreshPayload = """
                        {
                            "token": "%s"
                        }
                """.formatted(refreshToken);

        mockMvc.perform(post("/api/auth/refresh").contentType(MediaType.APPLICATION_JSON).content(refreshPayload)).andExpect(status().isOk());

        rootNode = mapper.readTree(response);
        accessToken = rootNode.get("accessToken").asText();
        mockMvc.perform(get("/api/tasks/my").header("Authorization", "Bearer " + accessToken)).andExpect(status().isOk());

        mockMvc.perform(post("/api/auth/logout").contentType(MediaType.APPLICATION_JSON).content(refreshPayload)).andExpect(status().isNoContent());
    }
}
