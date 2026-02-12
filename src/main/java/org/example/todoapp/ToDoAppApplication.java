package org.example.todoapp;

import org.example.todoapp.model.Role;
import org.example.todoapp.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity(debug = true)
public class ToDoAppApplication {
    private static final Logger logger = LoggerFactory.getLogger(ToDoAppApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ToDoAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(ApplicationContext ctx, RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0) {
                roleRepository.save(new Role("ROLE_ADMIN"));
                roleRepository.save(new Role("ROLE_USER"));
            }
            logger.info("CommandLineRunner");
        };
    }
}
