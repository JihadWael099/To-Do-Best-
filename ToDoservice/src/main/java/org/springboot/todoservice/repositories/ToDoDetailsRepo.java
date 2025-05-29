package org.springboot.todoservice.repositories;
import org.springboot.todoservice.entity.ToDoDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToDoDetailsRepo extends JpaRepository<ToDoDetails,Integer> {
    ToDoDetails findByIdAndUserId(int id, int userId);

    ToDoDetails findByEntityIdAndUserId(int id, int userId);
}
