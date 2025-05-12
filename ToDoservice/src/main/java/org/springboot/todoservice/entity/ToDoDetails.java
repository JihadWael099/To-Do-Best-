package org.springboot.todoservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springboot.todoservice.Enum.Priority;
import org.springboot.todoservice.Enum.Status;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Entity
@Validated
public class ToDoDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "you should  enter the priority")
    private Priority priority;

    @CreationTimestamp
    private LocalDateTime createAt;

    @NotNull(message = "you should  enter the start time")
    private LocalDateTime startAt;

    @NotNull(message = "you should  enter the end time")
    private LocalDateTime finishAt;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "you should  enter the status")
    private Status status;

    @OneToOne(mappedBy = "details_id" ,cascade = CascadeType.ALL)
    private TodoEntity todoEntity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getFinishAt() {
        return finishAt;
    }

    public void setFinishAt(LocalDateTime finishAt) {
        this.finishAt = finishAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public TodoEntity getTodoEntity() {
        return todoEntity;
    }

    public void setTodoEntity(TodoEntity todoEntity) {
        this.todoEntity = todoEntity;
    }

    public ToDoDetails(TodoEntity todoEntity, Status status, LocalDateTime finishAt, LocalDateTime startAt, LocalDateTime createAt, Priority priority, String description, int id) {
        this.todoEntity = todoEntity;
        this.status = status;
        this.finishAt = finishAt;
        this.startAt = startAt;
        this.createAt = createAt;
        this.priority = priority;
        this.description = description;
        this.id = id;
    }

    public ToDoDetails() {
    }

}
