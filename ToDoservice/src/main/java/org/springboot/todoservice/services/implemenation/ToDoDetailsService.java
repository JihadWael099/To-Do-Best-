package org.springboot.todoservice.services.implemenation;
import org.springboot.todoservice.entity.ToDoDetails;
import org.springboot.todoservice.entity.UserDto;
import org.springboot.todoservice.exception.NotFoundException;
import org.springboot.todoservice.repositories.ToDoDetailsRepo;
import org.springboot.todoservice.repositories.ToDoEntityRepo;
import org.springframework.stereotype.Service;
@Service
public class ToDoDetailsService {

    private final ToDoDetailsRepo toDoDetailsRepo;
    private final UserService userService;
    private final ToDoEntityRepo toDoEntityRepo;
    public ToDoDetailsService(ToDoDetailsRepo toDoDetailsRepo, UserService userService, ToDoEntityRepo toDoEntityRepo) {
        this.toDoDetailsRepo = toDoDetailsRepo;
        this.userService = userService;
        this.toDoEntityRepo = toDoEntityRepo;
    }
    private int validateUser(String token) {
        UserDto userDto = userService.validateToken(token);
        return userDto.getId();
    }
    public ToDoDetails updateDetails(ToDoDetails toDoDetails ,String token) throws NotFoundException {
        int userId=validateUser(token);
       ToDoDetails toDo = toDoDetailsRepo.findById(toDoDetails.getEntityId())
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

    public ToDoDetails viewDetailsById(int id,String token) throws NotFoundException {
        int userId=validateUser(token);
        return toDoDetailsRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("notfound"));
    }

    public ToDoDetails addDetails(ToDoDetails toDoDetails , String token) throws NotFoundException {
        int userId=validateUser(token);
        return toDoEntityRepo.findById(toDoDetails.getEntityId())
                .map(entity -> toDoDetailsRepo.save(toDoDetails))
                .orElseThrow(() -> new NotFoundException("ToDoEntity not found"));
    }

}
