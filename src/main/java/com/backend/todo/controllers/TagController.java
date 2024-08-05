package com.backend.todo.controllers;

import com.backend.todo.DTOs.ApiResponse;
import com.backend.todo.DTOs.TagNamesRequest;
import com.backend.todo.DTOs.TodoResponseDto;
import com.backend.todo.entities.Tag;
import com.backend.todo.services.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags/all")
    public ResponseEntity<ApiResponse<List<Tag>>> getAllTags() {
        try{
            var data = this.tagService.getAllTags();
            return ResponseEntity.ok().body(new ApiResponse<>(true, "Tags fetched successfully.", data));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Failed to fetch tags."));
        }
    }

    @PostMapping("/tags/new")
    public ResponseEntity<ApiResponse> addTag(
            @RequestBody TagNamesRequest tagNames
    ) {
        try {

            List<Tag> tags = tagNames.tagNames().stream().map(Tag::new).collect(Collectors.toList());
            this.tagService.saveTags(tags);
            return ResponseEntity.ok(new ApiResponse<>(true, "Tags created successfully"));

        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, exception.getMessage()));
        }
    }
}
