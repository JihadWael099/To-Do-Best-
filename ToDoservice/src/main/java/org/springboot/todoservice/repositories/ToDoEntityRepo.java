package org.springboot.todoservice.repositories;
import org.springboot.todoservice.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ToDoEntityRepo extends JpaRepository<TodoEntity, Integer> {
    List<TodoEntity> findAllByTitle(String title);

    List<TodoEntity> findAllByUserId(int userId);
}
