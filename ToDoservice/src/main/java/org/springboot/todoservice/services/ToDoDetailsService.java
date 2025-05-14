package org.springboot.todoservice.services;
import org.springboot.todoservice.entity.ToDoDetails;
import org.springboot.todoservice.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface ToDoDetailsService {

    public ToDoDetails updateDetails(ToDoDetails toDoDetails ,int id) throws NotFoundException;
    public ToDoDetails viewDetailsById( int id) throws NotFoundException;

    public ToDoDetails addDetails(ToDoDetails toDoDetails ) throws NotFoundException;

}
