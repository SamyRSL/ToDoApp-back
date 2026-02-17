package org.example.todoapp;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.example.todoapp.model.CustomUserDetails;
import org.example.todoapp.model.Task;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TasksTest extends BaseIntegrationTest {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        refreshTokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void createTaskTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        CustomUserDetails.RegisterRequestDTO registerRequestDTO = new CustomUserDetails.RegisterRequestDTO("user_test", "password_test");
        CustomUserDetails.LoginRequestDTO loginRequestDTO = new CustomUserDetails.LoginRequestDTO("user_test", "password_test");
        customUserDetailsService.register(registerRequestDTO);
        String response = mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(loginRequestDTO))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        JsonNode rootNode = objectMapper.readTree(response);
        String accessToken = rootNode.get("accessToken").asText();

        mockMvc.perform(get("/api/tasks/my").header("Authorization", "Bearer " + accessToken)).andExpect(status().isOk());
        String data = mockMvc.perform(get("/api/tasks/my").header("Authorization", "Bearer " + accessToken)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        log.info(data);
        Task.TaskNewDTO taskNewDTO = new Task.TaskNewDTO("task_test_1");
        mockMvc.perform(post("/api/tasks").header("Authorization", "Bearer " + accessToken).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(taskNewDTO))).andExpect(status().isOk());
        data = mockMvc.perform(get("/api/tasks/my").header("Authorization", "Bearer " + accessToken)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        log.info(data);
    }
}
