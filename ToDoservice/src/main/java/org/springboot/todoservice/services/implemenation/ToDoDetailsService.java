package org.springboot.todoservice.services.implemenation;
import org.springboot.todoservice.exception.NotFoundException;
import org.springboot.todoservice.repositories.ToDoDetailsRepo;
import org.springboot.todoservice.repositories.ToDoEntityRepo;
import org.springframework.stereotype.Service;
@Service
public class ToDoDetailsService {

    private final ToDoDetailsRepo toDoDetailsRepo;

    private final ToDoEntityRepo toDoEntityRepo;
    public ToDoDetailsService(ToDoDetailsRepo toDoDetailsRepo, ToDoEntityRepo toDoEntityRepo) {
        this.toDoDetailsRepo = toDoDetailsRepo;
        this.toDoEntityRepo = toDoEntityRepo;
    }


    public org.springboot.todoservice.entity.ToDoDetails updateDetails(org.springboot.todoservice.entity.ToDoDetails toDoDetails, int id) throws NotFoundException {
        org.springboot.todoservice.entity.ToDoDetails toDo = toDoDetailsRepo.findById(id)
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


    public org.springboot.todoservice.entity.ToDoDetails viewDetailsById(int id) throws NotFoundException {
        return toDoDetailsRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("notfound"));
    }


    public org.springboot.todoservice.entity.ToDoDetails addDetails(org.springboot.todoservice.entity.ToDoDetails toDoDetails) throws NotFoundException {
        return toDoEntityRepo.findById(toDoDetails.getEntityId())
                .map(entity -> toDoDetailsRepo.save(toDoDetails))
                .orElseThrow(() -> new NotFoundException("ToDoEntity not found"));
    }

}
