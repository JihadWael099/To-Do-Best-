package org.springboot.todoservice.controller;
import jakarta.validation.Valid;
import org.springboot.todoservice.entity.TodoEntity;
import org.springboot.todoservice.exception.NotFoundException;
import org.springboot.todoservice.services.implemenation.ToDoService;
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
    public ResponseEntity<TodoEntity> addToDo(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody TodoEntity todoEntity) {
        return ResponseEntity.ok(toDoService.addToDo(todoEntity, token));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeToDo(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") int id) throws NotFoundException {
        return ResponseEntity.ok(toDoService.removeToDo(id, token));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoEntity> viewToDoById(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") int id) throws NotFoundException {
        return ResponseEntity.ok(toDoService.viewToDoById(id,  token));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<TodoEntity>> viewToDoByTitle(
            @RequestHeader("Authorization") String token,
            @PathVariable("title") String title) {
        return ResponseEntity.ok(toDoService.viewToDoByTitle(title, token));
    }

    @GetMapping("/user")
    public ResponseEntity<List<TodoEntity>> viewByUserId(
            @RequestHeader("Authorization") String token,
            @RequestParam int id) {
        return ResponseEntity.ok(toDoService.viewByUserId(id, token));
    }

    @PutMapping("/title/{title}/id/{id}")
    public ResponseEntity<TodoEntity> updateTitle(
            @RequestHeader("Authorization") String token,
            @PathVariable("title") String title,
            @PathVariable("id") int id) throws NotFoundException {
        return ResponseEntity.ok(toDoService.updateTitle(title, id, token));
    }
}
