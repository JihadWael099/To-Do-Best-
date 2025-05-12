package org.springboot.todoservice.services;
import org.springboot.todoservice.entity.ToDoDetails;
import org.springframework.stereotype.Service;

@Service
public interface ToDoDetailsService {

    public ToDoDetails updateDetails(ToDoDetails toDoDetails ,int id);
    public ToDoDetails viewDetailsById( int id);

}
