package com.backend.todo.DTOs;

import java.util.List;

public class TodoCountsResponseWrapperDto {
    private List<TodoCountsResponseDto> counts;

    public TodoCountsResponseWrapperDto() {}

    public TodoCountsResponseWrapperDto(List<TodoCountsResponseDto> counts) {
        this.counts = counts;
    }

    public List<TodoCountsResponseDto> getCounts() {
        return counts;
    }

    public void setCounts(List<TodoCountsResponseDto> counts) {
        this.counts = counts;
    }
}
