package org.springboot.todoservice.repositories;

import org.springboot.todoservice.entity.ToDoDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoDetailsRepo extends JpaRepository<ToDoDetails,Integer> {
}
