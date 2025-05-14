package org.springboot.todoservice.services;

import org.springboot.todoservice.entity.TodoEntity;
import org.springboot.todoservice.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ToDoService {

    public TodoEntity addToDo(TodoEntity todoEntity);

    public String removeToDo(int id) throws NotFoundException;

    public TodoEntity viewToDoById(int id) throws NotFoundException;

    public List<TodoEntity> viewToDoByTitle(String title);

    public List<TodoEntity> viewByUserId(int id);
    public TodoEntity updateTitle(String title ,int id) throws NotFoundException;
}
