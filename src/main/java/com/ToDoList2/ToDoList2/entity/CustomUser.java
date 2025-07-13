package com.ToDoList2.ToDoList2.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customuser")
public class CustomUser {
    @Id @GeneratedValue
    private Integer id;

    @NotBlank(message = "Username is required")
    @Pattern(regexp = "^[a-zA-Z0-9._-]{3,20}$", message = "Username must be 3-20 characters with no spaces or special characters")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Column(name = "password")
    private String password;

    @OneToMany
    @JsonManagedReference
    @JoinColumn(name = "customUser_id")
    private List<ToDo> todoList;
}
