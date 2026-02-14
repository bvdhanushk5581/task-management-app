package com.dhanush.taskManagementApp.repository;

import com.dhanush.taskManagementApp.model.Task;
import com.dhanush.taskManagementApp.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCreatedByOrAssignedTo(Long createdBy, Long assignedTo);
    List<Task> findByCreatedByOrAssignedToAndStatus(Long createdBy, Long assignedTo, TaskStatus status);
}
