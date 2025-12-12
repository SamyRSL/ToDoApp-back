package org.example.todoapp.service;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.example.todoapp.controller.TaskController.TaskDTO;
import org.example.todoapp.model.CustomUserDetails;
import org.example.todoapp.model.Task;
import org.example.todoapp.repository.TaskRepository;
import org.example.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public Task createTask(TaskDTO taskDTO, CustomUserDetails customUserDetails) {
        String username = customUserDetails.getUsername();

        CustomUserDetails user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Task task = new Task(taskDTO.description());
        task.setUser(user);

        return taskRepository.save(task);
    }
}
