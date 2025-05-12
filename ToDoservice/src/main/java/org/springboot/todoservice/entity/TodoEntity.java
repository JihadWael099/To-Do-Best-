package org.springboot.todoservice.entity;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Entity

@Validated
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @NotBlank(message = "you should enter a title")
    private String title;

    private int userId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private ToDoDetails details_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ToDoDetails getDetails_id() {
        return details_id;
    }

    public void setDetails_id(ToDoDetails details_id) {
        this.details_id = details_id;
    }

    public TodoEntity(int id, String title, int userId, ToDoDetails details_id) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.details_id = details_id;
    }

    public TodoEntity() {
    }
}
