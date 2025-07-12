package com.ToDoList2.ToDoList2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ToDoList2.ToDoList2.entity.ToDo;

public interface ToDoRepository extends JpaRepository<ToDo, Integer> {
    public List<ToDo> findAllByCustomUserUsername(String username);
}