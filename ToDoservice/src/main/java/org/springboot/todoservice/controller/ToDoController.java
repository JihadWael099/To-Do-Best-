package org.springboot.todoservice.controller;

import jakarta.validation.Valid;
import org.springboot.todoservice.entity.TodoEntity;
import org.springboot.todoservice.exception.NotFoundException;
import org.springboot.todoservice.services.ToDoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/toDos")
public class ToDoController {
private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @PostMapping("")
    public ResponseEntity<TodoEntity> addToDo( @Valid @RequestBody TodoEntity todoEntity ){
        return ResponseEntity.ok(toDoService.addToDo(todoEntity)) ;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeToDo(@PathVariable("id") int id) throws NotFoundException {
        return ResponseEntity.ok(toDoService.removeToDo(id)) ;
    }

    @GetMapping("/{id}")
    ResponseEntity<TodoEntity> viewToDoById(@PathVariable("id") int id) throws NotFoundException {
        return ResponseEntity.ok(toDoService.viewToDoById(id));
    }

    @GetMapping("/title/{title}")
    ResponseEntity<List<TodoEntity>> viewToDoByTitle(@PathVariable("title") String title){
        return ResponseEntity.ok(toDoService.viewToDoByTitle(title));
    }

    @GetMapping("/user/{id}")
    ResponseEntity<List<TodoEntity>> viewByUserId(@PathVariable("id") int id){
        return ResponseEntity.ok(toDoService.viewByUserId(id));
    }

    @PutMapping("/title/{title}/id/{id}")
    ResponseEntity<TodoEntity> updateTitle(@PathVariable("title") String title,@PathVariable("id")int id) throws NotFoundException {
        return ResponseEntity.ok(toDoService.updateTitle(title,id));
    }


}
