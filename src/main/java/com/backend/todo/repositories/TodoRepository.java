package com.backend.todo.repositories;

import com.backend.todo.entities.Todo;
import com.backend.todo.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> findAllByTitleContaining(String searchTerm);

    List<Todo> findAllByStatus(Status status);

    @Query(value = """
            SELECT COUNT(*) FROM todos
            WHERE
            status = :status
            """, nativeQuery = true)
    Integer totalTodosCountByStatus(@Param("status") String status);
}
