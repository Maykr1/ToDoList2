package com.ToDoList2.ToDoList2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ToDoList2.ToDoList2.entity.CustomUser;

public interface CustomUserRepository extends JpaRepository<CustomUser, Integer> {
    public CustomUser findByUsername(String username);
}
