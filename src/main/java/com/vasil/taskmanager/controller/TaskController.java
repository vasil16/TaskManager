
package com.vasil.taskmanager.controller;

import com.vasil.taskmanager.dto.TaskDTO;
import com.vasil.taskmanager.dto.TaskResponse;
import com.vasil.taskmanager.entity.Task;
import com.vasil.taskmanager.entity.User;
import com.vasil.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public Task createTask(@RequestBody TaskDTO dto, Authentication auth) {
        User user = (User) auth.getPrincipal();
        return taskService.createTask(dto, user);
    }

    @GetMapping
    public List<TaskResponse> getTasks(Authentication auth) {
        User user = (User) auth.getPrincipal();
        return taskService.getUserTasks(user)
                .stream()
                .map(t -> new TaskResponse(t.getId(), t.getTitle(), t.getDescription(), t.isCompleted()))
                .toList();
    }

    @PutMapping("/{id}/done")
    public Task markDone(@PathVariable Long id, Authentication auth) {
        User user = (User) auth.getPrincipal();
        return taskService.markDone(id, user);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, Authentication auth) {
        User user = (User) auth.getPrincipal();
        taskService.deleteTask(id, user);
        return "deleted";
    }
}
