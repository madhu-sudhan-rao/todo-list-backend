package com.backend.todo.services;

import com.backend.todo.entities.Tag;
import com.backend.todo.repositories.TagRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag saveTag(Tag tag) {
        return this.tagRepository.save(tag);
    }

    public void saveTags(List<Tag> tags) {
        for (Tag tag : tags) {
            // Check if a tag with the same name already exists in the database
            Optional<Tag> existingTagOptional = this.tagRepository.findByName(tag.getName());

            if(existingTagOptional.isPresent()) {
                // If the tag already exists, update the current tag's ID to match the existing tag's ID
                Tag existingTag = existingTagOptional.get();
                tag.setId(existingTag.getId());
            } else {
                // If the tag does not exist, save it to the database
                Tag savedTag = this.saveTag(tag);
                tag.setId(savedTag.getId());
            }
        }
    }

    public List<Tag> getAllTags() {
        return new ArrayList<>(this.tagRepository.findAll());
    }
}
