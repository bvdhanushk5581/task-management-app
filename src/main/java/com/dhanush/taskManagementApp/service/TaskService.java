package com.dhanush.taskManagementApp.service;

import com.dhanush.taskManagementApp.dto.TaskRequest;
import com.dhanush.taskManagementApp.exception.UnauthorizedException;
import com.dhanush.taskManagementApp.model.Task;
import com.dhanush.taskManagementApp.model.TaskStatus;
import com.dhanush.taskManagementApp.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task createTask(TaskRequest request, Long createdById) {
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus() != null ? TaskStatus.valueOf(request.getStatus()) : TaskStatus.TODO)
                .createdAt(LocalDateTime.now())
                .assignedTo(request.getAssignedTo())
                .createdBy(createdById)
                .build();
        return taskRepository.save(task);
    }

    public List<Task> getTasks(Long userId, String status) {
        if (status != null && !status.isEmpty()) {
            TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
            return taskRepository.findByStatusAndCreatedByOrStatusAndAssignedTo(taskStatus, userId, taskStatus, userId);
        }
        return taskRepository.findByCreatedByOrAssignedTo(userId, userId);
    }

    public Task updateStatus(Long taskId, String status, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        if (!task.getCreatedBy().equals(userId) && !task.getAssignedTo().equals(userId)) {
            throw new UnauthorizedException("Only creator or assigned user can update status");
        }

        task.setStatus(TaskStatus.valueOf(status.toUpperCase()));
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        if (!task.getCreatedBy().equals(userId)) {
            throw new UnauthorizedException("Only creator can delete task");
        }

        taskRepository.delete(task);
    }
}
