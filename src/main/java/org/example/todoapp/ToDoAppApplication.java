package org.example.todoapp;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.todoapp.repository.TaskRepository;
import org.example.todoapp.model.Task;
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
            repository.save(new Task("Task 1"));
            repository.save(new Task("Task 10"));
            repository.save(new Task("Task 11"));
            repository.save(new Task("Task 100"));
            repository.save(new Task("Task 101"));

            logger.info("Tasks found with findAll():");
            logger.info("-------------------------------");
            repository.findAll().forEach(task -> {
                logger.info(task.toString());
            });
            logger.info("");

            Task task = repository.findById(1L);
            logger.info("Task found with findById(1L):");
            logger.info("--------------------------------");
            logger.info(task.toString());
            logger.info("");

            logger.info("Task found with findByDescription('Task 11'):");
            logger.info("--------------------------------------------");
            repository.findByDescription("Task 11").forEach(desc -> {
                logger.info(desc.toString());
            });
            logger.info("");

            /*System.out.println("Inspecting Beans");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }*/
        };
    }
}
