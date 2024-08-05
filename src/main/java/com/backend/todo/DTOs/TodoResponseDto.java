package com.backend.todo.DTOs;

import com.backend.todo.entities.Tag;
import com.backend.todo.enums.DuePeriod;
import com.backend.todo.enums.Status;

import java.util.List;

public record TodoResponseDto(
        Integer id,
        String title,
        String description,
        DuePeriod duePeriod,
        Status status,
        List<String> tags
) {
}
