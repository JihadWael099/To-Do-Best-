package org.springboot.todoservice.services.implemenation;

import org.springboot.todoservice.entity.ToDoDetails;
import org.springboot.todoservice.exception.NotFoundException;
import org.springboot.todoservice.repositories.ToDoDetailsRepo;
import org.springboot.todoservice.repositories.ToDoEntityRepo;
import org.springboot.todoservice.services.ToDoDetailsService;
import org.springframework.stereotype.Service;

@Service
public class ToDoDetailsImpl implements ToDoDetailsService {

    private final ToDoDetailsRepo toDoDetailsRepo;

    private final ToDoEntityRepo toDoEntityRepo;
    public ToDoDetailsImpl(ToDoDetailsRepo toDoDetailsRepo, ToDoEntityRepo toDoEntityRepo) {
        this.toDoDetailsRepo = toDoDetailsRepo;
        this.toDoEntityRepo = toDoEntityRepo;
    }

    @Override
    public ToDoDetails updateDetails(ToDoDetails toDoDetails, int id) throws NotFoundException {
        ToDoDetails toDo = toDoDetailsRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("notfound"));
        if(toDoDetails.getStatus()!=null)
            toDo.setStatus(toDoDetails.getStatus());
        if(toDoDetails.getFinishAt()!=null)
            toDo.setFinishAt(toDoDetails.getFinishAt());
        if(toDoDetails.getStartAt()!=null)
            toDo.setStartAt(toDoDetails.getStartAt());
        if(toDoDetails.getPriority()!=null)
            toDo.setPriority(toDoDetails.getPriority());
        if(toDoDetails.getDescription()!=null)
            toDo.setDescription(toDoDetails.getDescription());
        return  toDoDetailsRepo.save(toDo);
    }

    @Override
    public ToDoDetails viewDetailsById(int id) throws NotFoundException {
        return toDoDetailsRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("notfound"));
    }

    @Override
    public ToDoDetails addDetails(ToDoDetails toDoDetails) throws NotFoundException {
        return toDoEntityRepo.findById(toDoDetails.getEntityId())
                .map(entity -> toDoDetailsRepo.save(toDoDetails))
                .orElseThrow(() -> new NotFoundException("ToDoEntity not found"));
    }

}
