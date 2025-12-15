package org.example.todoapp.service;

import org.example.todoapp.model.Task.TaskNewDTO;
import org.example.todoapp.model.CustomUserDetails;
import org.example.todoapp.model.Task;
import org.example.todoapp.repository.TaskRepository;
import org.example.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(TaskNewDTO taskNewDTO, CustomUserDetails customUserDetails) {
        String username = customUserDetails.getUsername();

        CustomUserDetails user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Task task = new Task(taskNewDTO.description());
        task.setUser(user);

        return taskRepository.save(task);
    }

    public List<Task.TaskViewDTO> getTasks(CustomUserDetails user) {
        return taskRepository.findByUserID(user.getId()).stream().map(Task::toDTO).toList();
    }
}
