package org.springboot.todoservice.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @JsonIgnore
    private TodoEntity todoEntity;
    @Transient
    @NotNull(message = "entity ID is required")
    private int  entityId;
    @JsonProperty("entityId")
    public int  getEntityId() {
        return todoEntity != null ? todoEntity.getId() : entityId;
    }


}
