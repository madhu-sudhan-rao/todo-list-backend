package com.backend.todo.services;

import com.backend.todo.DTOs.*;
import com.backend.todo.entities.Todo;
import com.backend.todo.enums.Status;
import com.backend.todo.exceptions.TodoNotFoundException;
import com.backend.todo.repositories.TagRepository;
import com.backend.todo.repositories.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;
    private final TagRepository tagRepository;
    private final TagService tagService;


    public TodoService(TodoRepository todoRepository, TodoMapper todoMapper, TagRepository tagRepository, TagService tagService) {
        this.todoRepository = todoRepository;
        this.todoMapper = todoMapper;
        this.tagRepository = tagRepository;
        this.tagService = tagService;
    }

    public List<TodoResponseDto> getAllTodos() {
        return this.todoRepository.findAll()
                .stream()
                .map(this.todoMapper::toTodoResponseDto)
                .collect(Collectors.toList());
    }

    public TodoResponseDto addTodo(TodoRequestDto todoRequestDto) throws Exception {
        try {
            var todo = this.todoMapper.toTodo(todoRequestDto);
            this.tagService.saveTags(todo.getTags());
            var savedTodo = this.todoRepository.save(todo);
            return this.todoMapper.toTodoResponseDto(savedTodo);
        } catch (Exception exception) {
            throw new Exception("Failed to create todo item.");
        }
    }

    public TodoResponseDto getTodoById(Integer id) throws TodoNotFoundException {
        return todoRepository.findById(id)
                .map(todoMapper::toTodoResponseDto)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));
    }

    public boolean deleteTodoById(Integer id) throws TodoNotFoundException {
        Optional<Todo> optionalTodo = this.todoRepository.findById(id);
        if(optionalTodo.isEmpty()) {
            throw new TodoNotFoundException("Todo not found with id: " + id);
        }
        this.todoRepository.deleteById(id);
        return true;
    }

    public TodoResponseDto editTodoById(Integer id, TodoRequestDto todoRequestDto) {
        Optional<Todo> t = this.todoRepository.findById(id);
        if (t.isPresent()) {
            var existingTodo = t.get();

            // Convert TodoRequestDto to Todo entity
            var updatedTodo = this.todoMapper.toTodo(todoRequestDto);

            // Save any new tags before updating the Todo
            this.tagService.saveTags(updatedTodo.getTags());

            // Update the existing Todo with the new data
            existingTodo.setTags(updatedTodo.getTags());
            existingTodo.setStatus(updatedTodo.getStatus());
            existingTodo.setDuePeriod(updatedTodo.getDuePeriod());
            existingTodo.setTitle(updatedTodo.getTitle());
            existingTodo.setDescription(updatedTodo.getDescription());

            // Save the updated Todo
            var savedTodo = this.todoRepository.save(existingTodo);

            return this.todoMapper.toTodoResponseDto(savedTodo);
        }
        return  null;
    }


    public TodoUpdateResponseDto updateTodoStatus(Integer id, String status) throws TodoNotFoundException {
        Optional<Todo> optionalTodo = this.todoRepository.findById(id);
        if(optionalTodo.isEmpty()) {
            throw new TodoNotFoundException("Todo not found with id: " + id);
        }

        var existingTodo = optionalTodo.get();
        try{
            existingTodo.setStatus(Status.valueOf(status));
            this.todoRepository.save(existingTodo);
            return this.todoMapper.toTodoUpdateResponseDto(existingTodo);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Invalid status value: " + status);
        }
    }

    public List<TodoResponseDto> searchTodosByName(String searchTerm) {
        return this.todoRepository.findAllByTitleContaining(searchTerm)
                .stream()
                .map(this.todoMapper::toTodoResponseDto)
                .collect(Collectors.toList());
    }

    public List<TodoResponseDto> getTodosByStatus(Status status) {
        return this.todoRepository.findAllByStatus(status)
                .stream()
                .map(this.todoMapper::toTodoResponseDto)
                .collect(Collectors.toList());
    }

    public TodoCountsResponseWrapperDto getTodosCount() {
        var completedTodosCount = this.todoRepository.totalTodosCountByStatus(Status.COMPLETED.toString());
        var pendingTodosCount = this.todoRepository.totalTodosCountByStatus(Status.PENDING.toString());
        var inProgressTodosCount = this.todoRepository.totalTodosCountByStatus(Status.IN_PROGRESS.toString());
        var totalTodosCount = completedTodosCount + pendingTodosCount + inProgressTodosCount;

        List<TodoCountsResponseDto> counts = List.of(
                new TodoCountsResponseDto(Status.COMPLETED.toString(), completedTodosCount),
                new TodoCountsResponseDto(Status.PENDING.toString(), pendingTodosCount),
                new TodoCountsResponseDto(Status.IN_PROGRESS.toString(), inProgressTodosCount),
                new TodoCountsResponseDto("ALL", totalTodosCount)
        );

        return new TodoCountsResponseWrapperDto(counts);
    }
}
