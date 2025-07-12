package com.ToDoList2.ToDoList2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
}
