package org.springboot.todoservice.repositories;
import org.springboot.todoservice.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ToDoEntityRepo extends JpaRepository<TodoEntity, Integer> {
    List<TodoEntity> findAllByTitleAndUserId(String title,int id);
    Optional<TodoEntity> findByIdAndUserId(int id, int userId);
    List<TodoEntity> findAllByUserId(int userId);
}
