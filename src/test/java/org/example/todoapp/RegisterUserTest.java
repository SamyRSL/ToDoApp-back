package org.example.todoapp;

import org.example.todoapp.model.CustomUserDetails;
import org.example.todoapp.repository.UserRepository;
import org.example.todoapp.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class RegisterUserTest extends BaseIntegrationTest {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void registerUserTest() {
        CustomUserDetails.RegisterRequestDTO registerRequestDTO = new CustomUserDetails.RegisterRequestDTO("user_test", "password_test");
        customUserDetailsService.register(registerRequestDTO);
        CustomUserDetails user = userRepository.findByUsername("user_test").orElseThrow();

        assertEquals("user_test", user.getUsername());
        assertNotNull(user.getPassword());
        assertNotEquals("password_test", user.getPassword());
    }

}
