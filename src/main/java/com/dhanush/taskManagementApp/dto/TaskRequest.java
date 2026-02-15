package com.dhanush.taskManagementApp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskRequest {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    private Long assignedTo;
    private String status;
}
