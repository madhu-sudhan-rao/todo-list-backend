package com.backend.todo.DTOs;

import java.util.List;

public record TagNamesRequest(
        List<String> tagNames
) {
}
