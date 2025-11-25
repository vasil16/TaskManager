
package com.vasil.taskmanager.service;

import com.vasil.taskmanager.dto.TaskDTO;
import com.vasil.taskmanager.entity.Task;
import com.vasil.taskmanager.entity.User;
import com.vasil.taskmanager.exception.AppException;
import com.vasil.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task createTask(TaskDTO dto, User user) {
        Task t = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
        return taskRepository.save(t);
    }

    public List<Task> getUserTasks(User user) {
        return taskRepository.findByUserId(user.getId());
    }

    public Task markDone(Long id, User user) {
        Task t = taskRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new AppException("Task not found", HttpStatus.NOT_FOUND));
        t.setCompleted(true);
        return taskRepository.save(t);
    }

    public void deleteTask(Long id, User user) {
        Task t = taskRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Task not found"));
        taskRepository.delete(t);
    }
}
