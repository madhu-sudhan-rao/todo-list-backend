package com.backend.todo.entities;

import com.backend.todo.enums.DuePeriod;
import com.backend.todo.enums.Status;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer todo_id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private DuePeriod duePeriod;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

//    @ManyToMany
//    @JoinTable(
//            name = "todo_tags",
//            joinColumns = @JoinColumn(name = "todo_id"),
//            inverseJoinColumns = @JoinColumn(name = "tag_id")
//    )
//    private List<Tag> tags;

    @ManyToMany(mappedBy = "todos")
    private List<Tag> tags;

    public Integer getTodo_id() {
        return todo_id;
    }

    public void setTodo_id(Integer todo_id) {
        this.todo_id = todo_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DuePeriod getDuePeriod() {
        return duePeriod;
    }

    public void setDuePeriod(DuePeriod duePeriod) {
        this.duePeriod = duePeriod;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Todo() {
    }

    public Todo(String title, String description, DuePeriod duePeriod, Status status) {
        this.title = title;
        this.description = description;
        this.duePeriod = duePeriod;
        this.status = status;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
