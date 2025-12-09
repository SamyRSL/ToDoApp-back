package org.example.todoapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.todoapp.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ToDoAppApplication {
    private static final Logger logger = LoggerFactory.getLogger(ToDoAppApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ToDoAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(ApplicationContext ctx, TaskRepository repository) {
        return args -> {
            logger.info("CommandLineRunner");
        };
    }
}
