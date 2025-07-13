package com.ToDoList2.ToDoList2.entity;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Entity @Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TODO")
public class ToDo {
    @Id @GeneratedValue
    private Integer id;

    @NotBlank(message = "Title is required")
    @Column(name="TITLE")
    private String title;

    @Column(name="DESCRIPTION")
    private String description;

    @NotNull(message = "Due date is required")
    @Column(name="DUEDATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    @Column(name="COMPLETED")
    private Boolean completed;

    @ManyToOne
    @JoinColumn(name = "customUser_id")
    @JsonBackReference
    private CustomUser customUser;
}
