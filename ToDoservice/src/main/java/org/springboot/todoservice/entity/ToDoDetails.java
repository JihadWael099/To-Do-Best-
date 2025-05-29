package org.springboot.todoservice.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ToDoDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "you should enter the priority")
    private Priority priority;

    @CreationTimestamp
    private LocalDateTime createAt;

    @NotNull(message = "you should enter the start time")
    private LocalDateTime startAt;

    @NotNull(message = "you should enter the end time")
    private LocalDateTime finishAt;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "you should enter the status")
    private Status status;

    @OneToOne(mappedBy = "toDoDetails", cascade = CascadeType.ALL)
    @JsonIgnore
    private TodoEntity todoEntity;
    
    private int entityId;

    @JsonIgnore
    private int userId;

    @JsonProperty("entityId")
    public int getEntityId() {
        return todoEntity != null ? todoEntity.getId() : entityId;
    }
}
