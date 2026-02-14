package com.dhanush.taskManagementApp.controller;

import com.dhanush.taskManagementApp.dto.TaskRequest;
import com.dhanush.taskManagementApp.dto.UpdateStatusRequest;
import com.dhanush.taskManagementApp.model.Task;
import com.dhanush.taskManagementApp.model.User;
import com.dhanush.taskManagementApp.repository.UserRepository;
import com.dhanush.taskManagementApp.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskRequest request,
                                           Authentication auth) {
        User currentUser = userRepository.findByEmail(auth.getName()).orElseThrow();
        return ResponseEntity.ok(taskService.createTask(request, currentUser.getId()));
    }
    @GetMapping
    public ResponseEntity<List<Task>> getTasks(Authentication auth,
                                               @RequestParam(required = false) String status) {
        User currentUser = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(taskService.getTasks(currentUser.getId(), status));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> updateStatus(@PathVariable Long id,
                                             @Valid @RequestBody UpdateStatusRequest request,
                                             Authentication auth) {
        User currentUser = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(taskService.updateStatus(id, request.getStatus(), currentUser.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, Authentication auth) {
        User currentUser = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        taskService.deleteTask(id, currentUser.getId());
        return ResponseEntity.noContent().build();
    }
}
