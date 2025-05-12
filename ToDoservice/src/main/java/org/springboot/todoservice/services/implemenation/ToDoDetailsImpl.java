package org.springboot.todoservice.services.implemenation;

import lombok.RequiredArgsConstructor;
import org.springboot.todoservice.entity.ToDoDetails;
import org.springboot.todoservice.repositories.ToDoDetailsRepo;
import org.springboot.todoservice.services.ToDoDetailsService;
import org.springframework.stereotype.Service;

@Service
public class ToDoDetailsImpl implements ToDoDetailsService {

    private final ToDoDetailsRepo toDoDetailsRepo;

    public ToDoDetailsImpl(ToDoDetailsRepo toDoDetailsRepo) {
        this.toDoDetailsRepo = toDoDetailsRepo;
    }

    @Override
    public ToDoDetails updateDetails(ToDoDetails toDoDetails, int id) {
        ToDoDetails toDo = toDoDetailsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("notfound"));
        if(toDoDetails.getStatus()!=null)
            toDo.setStatus(toDoDetails.getStatus());
        if(toDoDetails.getFinishAt()!=null)
            toDo.setFinishAt(toDoDetails.getFinishAt());
        if(toDoDetails.getStartAt()!=null)
            toDo.setStartAt(toDoDetails.getStartAt());
        if(toDoDetails.getPriority()!=null)
            toDo.setPriority(toDoDetails.getPriority());
        if(toDoDetails.getTodoEntity().getTitle()!=null)
            toDo.getTodoEntity().setTitle(toDoDetails.getTodoEntity().getTitle());
        if(toDoDetails.getDescription()!=null)
            toDo.setDescription(toDoDetails.getDescription());
        return  toDoDetailsRepo.save(toDo);
    }

    @Override
    public ToDoDetails viewDetailsById(int id) {
        return toDoDetailsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("notfound"));
    }
}
