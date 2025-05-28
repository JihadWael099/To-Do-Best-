package org.springboot.todoservice.services.implemenation;
import org.springboot.todoservice.entity.TodoEntity;
import org.springboot.todoservice.entity.UserDto;
import org.springboot.todoservice.exception.NotFoundException;
import org.springboot.todoservice.exception.TokenValidationException;
import org.springboot.todoservice.repositories.ToDoEntityRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class ToDoService {

    private final ToDoEntityRepo toDoEntityRepo;
    private final UserService userService;

    public ToDoService(ToDoEntityRepo toDoEntityRepo, UserService userService) {
        this.toDoEntityRepo = toDoEntityRepo;
        this.userService = userService;
    }

    private int validateUser(String token) {
        UserDto userDto = userService.validateToken(token);
        return userDto.getId();
    }
    private UserDto validateUserName(String token) {
        return userService.validateToken(token);
    }

    @Transactional
    public TodoEntity addToDo(TodoEntity todoEntity, String token) {
        System.out.println(todoEntity.toString());
        int userId=validateUser(token);
        if (todoEntity.getUserId() == userId) {
            TodoEntity todo = new TodoEntity();
            todo.setTitle(todoEntity.getTitle());
            todo.setUserId(todoEntity.getUserId());
            todo.setDetails_id(todoEntity.getDetails_id());
            toDoEntityRepo.save(todo);
            return todo;
        }
        else throw new TokenValidationException("user is not valid");
    }

    @Transactional
    public String removeToDo(int id,String token) throws NotFoundException {
        int userId=validateUser( token);
        Optional<TodoEntity> todoEntity = toDoEntityRepo.findByIdAndUserId(id,userId);
        if (todoEntity.isEmpty()) throw new NotFoundException("not found to remove");
        toDoEntityRepo.delete(todoEntity.get());
        return "deleted";
    }

    @Transactional
    public TodoEntity viewToDoById(int id, String token) throws NotFoundException {
        int userId=validateUser(token);
        return toDoEntityRepo.findByIdAndUserId(id,userId).orElseThrow(() -> new NotFoundException("not found"));
    }

    @Transactional
    public List<TodoEntity> viewToDoByTitle(String title,String token) {
        int userId=validateUser( token);
        return toDoEntityRepo.findAllByTitleAndUserId(title,userId);
    }

    @Transactional
    public List<TodoEntity> viewByUserName(String name, String token) {
        UserDto user=validateUserName(token);
        if (Objects.equals(user.getUsername(), name)) {
            List<TodoEntity> todos = toDoEntityRepo.findAllByUserId(user.getId());
            todos.sort(Comparator.comparing(
                    todo -> todo.getDetails_id().getPriority()
            ));
            return todos;
        }
        else throw new TokenValidationException("user is not valid");
    }

    @Transactional
    public TodoEntity updateTitle(String title, int id, String token) throws NotFoundException {
        int userId=validateUser( token);
        TodoEntity todoEntity = toDoEntityRepo.findByIdAndUserId(id,userId)
                .orElseThrow(() -> new NotFoundException("not found"));
        todoEntity.setTitle(title);
        return toDoEntityRepo.save(todoEntity);
    }

}
