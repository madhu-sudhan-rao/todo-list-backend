package com.backend.todo.services;

import com.backend.todo.DTOs.TodoRequestDto;
import com.backend.todo.DTOs.TodoResponseDto;
import com.backend.todo.DTOs.TodoUpdateResponseDto;
import com.backend.todo.entities.Tag;
import com.backend.todo.entities.Todo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoMapper {

    public Todo toTodo(TodoRequestDto todoRequestDto) {
        var todo = new Todo();
        todo.setTitle(todoRequestDto.title());
        todo.setDescription(todoRequestDto.description());
        todo.setStatus(todoRequestDto.status());
        todo.setDuePeriod(todoRequestDto.duePeriod());

        List<Tag> tags = new ArrayList<>();
        todoRequestDto.tags().forEach(tag -> {
            Tag t = new Tag();
            t.setName(tag);
            tags.add(t);
        });
        todo.setTags(tags);

        return todo;
    }

    public TodoResponseDto toTodoResponseDto(Todo todo) {
        List<String> tagNames = todo.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.toList());

        return new TodoResponseDto(
                todo.getTodo_id(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getDuePeriod(),
                todo.getStatus(),
                tagNames
        );
    }

    public TodoUpdateResponseDto toTodoUpdateResponseDto(Todo todo) {
        return new TodoUpdateResponseDto(todo.getStatus(), todo.getTodo_id());
    }
}
