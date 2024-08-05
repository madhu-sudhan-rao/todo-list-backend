package com.backend.todo.DTOs;

import com.backend.todo.enums.Status;

public record TodoUpdateResponseDto(
        Status status,
        Integer id
) {
}
