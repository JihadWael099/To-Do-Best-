package org.springboot.todoservice.controller;

import org.springboot.todoservice.entity.ToDoDetails;
import org.springboot.todoservice.exception.NotFoundException;
import org.springboot.todoservice.repositories.ToDoDetailsRepo;
import org.springboot.todoservice.services.ToDoDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/toDoDetails")
public class ToDoDetailsController {

    private final ToDoDetailsService toDoDetailsService;

    public ToDoDetailsController(ToDoDetailsService toDoDetailsService) {
        this.toDoDetailsService = toDoDetailsService;
    }

    @PostMapping("")
    public ResponseEntity<ToDoDetails> addDetails(@RequestBody ToDoDetails toDoDetails) throws NotFoundException {
        return ResponseEntity.ok(toDoDetailsService.addDetails(toDoDetails));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ToDoDetails> updateDetails(@RequestBody ToDoDetails toDoDetails,@PathVariable("id") int id) throws NotFoundException {
        return ResponseEntity.ok(toDoDetailsService.updateDetails(toDoDetails,id));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ToDoDetails> viewDetails(@PathVariable("id") int id) throws NotFoundException {
        return ResponseEntity.ok(toDoDetailsService.viewDetailsById(id));
    }

}
