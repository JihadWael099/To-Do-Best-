package org.springboot.todoservice.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Entity
@Validated
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "you should enter a title")
    private String title;
    @NotBlank(message = "you should enter a user id")
    private int userId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private ToDoDetails details_id;

}
