package com.backend.todo.controllers;

import com.backend.todo.DTOs.*;
import com.backend.todo.enums.Status;
import com.backend.todo.exceptions.TodoNotFoundException;
import com.backend.todo.services.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todos")
    public ResponseEntity<ApiResponse<List<TodoResponseDto>>> getAllTodos() {
        try {
            var data = this.todoService.getAllTodos();
            return ResponseEntity.ok(new ApiResponse<List<TodoResponseDto>>(true, "Todo items fetched successfully", data));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Something error occurred."));
        }
    }

    @GetMapping("/todos/{todo-id}")
    public ResponseEntity<ApiResponse<TodoResponseDto>> getTodoById(
            @PathVariable("todo-id") Integer id
    ) {
        try {
            var data = todoService.getTodoById(id);
            return ResponseEntity.ok(new ApiResponse<TodoResponseDto>(true, "Todo item fetched successfully", data));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, exception.getMessage()));
        }
    }

    @PostMapping("/todos/new")
    public ResponseEntity<ApiResponse<TodoResponseDto>> addTodo(
            @Valid @RequestBody TodoRequestDto todoRequestDto
    ) {
        try {
            var data = this.todoService.addTodo(todoRequestDto);
            return ResponseEntity.ok(new ApiResponse<TodoResponseDto>(true, "Todo item created successfully", data));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, exception.getMessage()));
        }
    }

    @DeleteMapping("delete/{todo-id}")
    public ResponseEntity<ApiResponse<DeleteResponseDto>> deleteTodoById(
            @PathVariable("todo-id") Integer id
    ) {
        try {
            if (this.todoService.deleteTodoById(id)) {
                return ResponseEntity.ok().body(new ApiResponse<DeleteResponseDto>(true, "Deleted successfully."));
            } else {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Failed to delete todo item."));
            }
        } catch (IllegalArgumentException | TodoNotFoundException exception) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, exception.getMessage()));
        }
    }

    @PutMapping("/todo/edit/{todo-id}")
    public TodoResponseDto editTodo(
            @PathVariable("todo-id") Integer id,
            @RequestBody TodoRequestDto todoRequestDto
    ) {
        return this.todoService.editTodoById(id, todoRequestDto);
    }

    @PutMapping("todos/{todo-id}")
    public ResponseEntity<ApiResponse> updateTodoStatus(
            @RequestParam String status,
            @PathVariable("todo-id") Integer id
    ) {
        try{
            var data = this.todoService.updateTodoStatus(id,status);
            return ResponseEntity.ok(new ApiResponse<>(true,"Status updated successfully.", data));
        } catch (IllegalArgumentException | TodoNotFoundException exception) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, exception.getMessage()));
        }
    }

    @GetMapping("/todos/search")
    public ResponseEntity<ApiResponse<List<TodoResponseDto>>> searchTodo(
            @RequestParam String searchTerm
    ) {
       try{
           var data = this.todoService.searchTodosByName(searchTerm);
           return ResponseEntity.ok().body(new ApiResponse<>(true, "Matched todos fetched", data));
       } catch (IllegalArgumentException exception) {
           return ResponseEntity.badRequest().body(new ApiResponse<>(false, exception.getMessage()));
       }
    }

    @GetMapping("/todos/filter")
    public ResponseEntity<ApiResponse<List<TodoResponseDto>>> getTodosByStatus(@RequestParam Status status) {
        try {
            var data = this.todoService.getTodosByStatus(status);
            return ResponseEntity.ok().body(new ApiResponse<>(true, "Filtered todos successfully", data));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, exception.getMessage()));
        }
    }

    @GetMapping("/todos/counts")
    public ResponseEntity<ApiResponse<TodoCountsResponseWrapperDto>> getTodosCounts() {
        var data = this.todoService.getTodosCount();
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Todos counts.", data));

    }


}
