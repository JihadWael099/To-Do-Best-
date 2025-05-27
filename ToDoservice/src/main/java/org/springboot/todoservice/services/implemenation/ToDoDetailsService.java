package org.springboot.todoservice.services.implemenation;
import org.springboot.todoservice.entity.ToDoDetails;
import org.springboot.todoservice.entity.UserDto;
import org.springboot.todoservice.exception.NotFoundException;
import org.springboot.todoservice.repositories.ToDoDetailsRepo;
import org.springboot.todoservice.repositories.ToDoEntityRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public ToDoDetails updateDetails(ToDoDetails toDoDetails ,int id,String token) throws NotFoundException {
        int userId=validateUser(token);
        if(userId >= 0) {
            ToDoDetails toDo = toDoDetailsRepo.findByIdAndUserId(id, userId);
            if (toDo !=null) {
                if (toDoDetails.getStatus() != null)
                    toDo.setStatus(toDoDetails.getStatus());
                if (toDoDetails.getFinishAt() != null)
                    toDo.setFinishAt(toDoDetails.getFinishAt());
                if (toDoDetails.getStartAt() != null)
                    toDo.setStartAt(toDoDetails.getStartAt());
                if (toDoDetails.getPriority() != null)
                    toDo.setPriority(toDoDetails.getPriority());
                if (toDoDetails.getDescription() != null)
                    toDo.setDescription(toDoDetails.getDescription());
                return toDoDetailsRepo.save(toDo);
            }
            else throw  new NotFoundException("todo is not found");
        }
        else throw new NotFoundException("user is not found");
    }
    @Transactional
    public ToDoDetails viewDetailsById(int id,String token) throws NotFoundException {
        int userId=validateUser(token);
        if (userId >=0) {
            ToDoDetails toDo=toDoDetailsRepo.findByIdAndUserId(id, userId);
            if (toDo !=null){
                return toDo;
            }
            else throw new NotFoundException("todo is not found");
        }
        else throw new NotFoundException("user is not found");
    }
    @Transactional
    public ToDoDetails addDetails(ToDoDetails toDoDetails , String token) throws NotFoundException {
        int userId=validateUser(token);
        toDoDetails.setUserId(userId);
        return toDoEntityRepo.findByIdAndUserId(toDoDetails.getEntityId(),userId)
                .map(entity -> toDoDetailsRepo.save(toDoDetails))
                .orElseThrow(() -> new NotFoundException("ToDoEntity not found"));
    }

}
