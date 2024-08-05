package com.backend.todo.DTOs;

import com.backend.todo.enums.Status;

public record TodoCountsResponseDto(
        String status,

        Integer count
) {
}
