package com.bifani.taskmanagerjava.dto;

import com.bifani.taskmanagerjava.database.model.TaskEntity;
import com.bifani.taskmanagerjava.database.model.TaskEnum;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskEnum status){

    public TaskResponse(TaskEntity entity) {
        this(entity.getId(), entity.getTitle(), entity.getDescription(), entity.getStatus());
    }
}
