package org.springboot.todoservice.services.implemenation;
import jakarta.transaction.TransactionScoped;
import org.springboot.todoservice.entity.TodoEntity;
import org.springboot.todoservice.repositories.ToDoEntityRepo;
import org.springboot.todoservice.services.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class ToDoServiceImpl implements ToDoService {

    private final ToDoEntityRepo toDoEntityRepo;
    public ToDoServiceImpl(ToDoEntityRepo toDoEntityRepo) {
        this.toDoEntityRepo = toDoEntityRepo;
    }

    @Transactional
    @Override
    public TodoEntity addToDo(TodoEntity todoEntity) {
    TodoEntity todo =new TodoEntity();
    todo.setTitle(todoEntity.getTitle());
    todo.setUserId(todoEntity.getUserId());
    todo.setDetails_id(todoEntity.getDetails_id());
    toDoEntityRepo.save(todo);
    return todo;
    }

    @Transactional
    @Override
    public String removeToDo(int id) {
        Optional<TodoEntity> todoEntity = toDoEntityRepo.findById(id);
        if(todoEntity.isEmpty())return "not found to delete";
        else toDoEntityRepo.delete(todoEntity.get());
        return "deleted";
    }

    @Transactional
    @Override
    public TodoEntity viewToDoById(int id) {
        Optional<TodoEntity> todoEntity = toDoEntityRepo.findById(id);
        if(todoEntity.isEmpty())throw new RuntimeException("not found");
        else return todoEntity.get();
    }

    @Transactional
    @Override
    public List<TodoEntity> viewToDoByTitle(String title) {
        return toDoEntityRepo.findAllByTitle(title);
    }

    @Transactional
    @Override
    public List<TodoEntity> viewByUserId(int id) {
        return toDoEntityRepo.findAllByUserId(id);
    }
}
