package org.springboot.todoservice.services;

import org.springboot.todoservice.entity.TodoEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ToDoService {

    public TodoEntity addToDo(TodoEntity todoEntity);

    public String removeToDo(int id);

    public TodoEntity viewToDoById(int id);

    public List<TodoEntity> viewToDoByTitle(String title);

    public List<TodoEntity> viewByUserId(int id);
}
