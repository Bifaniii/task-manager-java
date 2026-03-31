package com.bifani.taskmanagerjava.dto;

import com.bifani.taskmanagerjava.database.model.TaskEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskRequest (
        @NotBlank(message = "O título é obrigatório")
        String title,
        @NotBlank(message = "A descrição é obrigatória")
        String description
) {}
