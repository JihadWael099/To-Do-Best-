package org.springboot.todoservice.controller;
import org.springboot.todoservice.entity.ToDoDetails;
import org.springboot.todoservice.exception.NotFoundException;
import org.springboot.todoservice.services.implemenation.ToDoDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("api/v1/toDoDetails")
@CrossOrigin(origins = "http://localhost:4200")
public class ToDoDetailsController {
    private final ToDoDetailsService toDoDetailsService;
    public ToDoDetailsController(ToDoDetailsService toDoDetailsService) {
        this.toDoDetailsService = toDoDetailsService;
    }
    @PostMapping("")
    public ResponseEntity<ToDoDetails> addDetails(
            @RequestBody ToDoDetails toDoDetails,
            @RequestHeader("Authorization") String token) throws NotFoundException {
        return ResponseEntity.ok(toDoDetailsService.addDetails(toDoDetails,token));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ToDoDetails> updateDetails(@RequestBody ToDoDetails toDoDetails,
                                                     @RequestHeader("Authorization") String token,
                                                     @PathVariable("id") int id) throws NotFoundException {
        return ResponseEntity.ok(toDoDetailsService.updateDetails(toDoDetails,id,token));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ToDoDetails> viewDetails(@PathVariable("id") int id, @RequestHeader("Authorization") String token) throws NotFoundException {
        return ResponseEntity.ok(toDoDetailsService.viewDetailsById(id ,token));
    }
}
