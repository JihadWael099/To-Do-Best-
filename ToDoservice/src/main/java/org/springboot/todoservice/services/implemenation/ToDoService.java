package org.springboot.todoservice.services.implemenation;
import org.springboot.todoservice.entity.TodoEntity;
import org.springboot.todoservice.exception.NotFoundException;
import org.springboot.todoservice.repositories.ToDoEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class ToDoService {

    private final ToDoEntityRepo toDoEntityRepo;
    private final UserService userService;

    public ToDoService(ToDoEntityRepo toDoEntityRepo, UserService userService) {
        this.toDoEntityRepo = toDoEntityRepo;
        this.userService = userService;
    }

    private void validateUser(int userId, String token) {
        if (!userService.validateToken(token)) {
            throw new RuntimeException("Invalid or expired token");
        }
        try {
            userService.getUserById(userId, token);
        } catch (Exception e) {
            throw new RuntimeException("Invalid user");
        }
    }

    @Transactional
    public TodoEntity addToDo(TodoEntity todoEntity, String token) {
        validateUser(todoEntity.getUserId(), token);
        TodoEntity todo = new TodoEntity();
        todo.setTitle(todoEntity.getTitle());
        todo.setUserId(todoEntity.getUserId());
        todo.setDetails_id(todoEntity.getDetails_id());
        toDoEntityRepo.save(todo);
        return todo;
    }

    @Transactional
    public String removeToDo(int id, int userId, String token) throws NotFoundException {
        validateUser(userId, token);
        Optional<TodoEntity> todoEntity = toDoEntityRepo.findByIdAndUserId(id,userId);
        if (todoEntity.isEmpty()) throw new NotFoundException("not found to remove");
        toDoEntityRepo.delete(todoEntity.get());
        return "deleted";
    }

    @Transactional
    public TodoEntity viewToDoById(int id, int userId, String token) throws NotFoundException {
        validateUser(userId, token);
        return toDoEntityRepo.findByIdAndUserId(id,userId).orElseThrow(() -> new NotFoundException("not found"));
    }

    @Transactional
    public List<TodoEntity> viewToDoByTitle(String title, int userId, String token) {
        validateUser(userId, token);
        return toDoEntityRepo.findAllByTitleAndUserId(title,userId);
    }

    @Transactional
    public List<TodoEntity> viewByUserId(int id, String token) {
        validateUser(id, token);
        return toDoEntityRepo.findAllByUserId(id);
    }

    @Transactional

    public TodoEntity updateTitle(String title, int id, int userId, String token) throws NotFoundException {
        validateUser(userId, token);

        TodoEntity todoEntity = toDoEntityRepo.findById(id).orElseThrow(() -> new NotFoundException("not found"));
        todoEntity.setTitle(title);
        return toDoEntityRepo.save(todoEntity);
    }
}
