package com.backend.todo.DTOs;

import com.backend.todo.entities.Tag;
import com.backend.todo.enums.DuePeriod;
import com.backend.todo.enums.Status;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record TodoRequestDto(
        @NotEmpty
        String title,
        String description,
        DuePeriod duePeriod,
        Status status,
        List<String> tags
) {
}
