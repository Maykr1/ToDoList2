package com.ToDoList2.ToDoList2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ToDoList2.ToDoList2.entity.ToDo;
import com.ToDoList2.ToDoList2.service.ToDoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@Slf4j
@RestController
@RequestMapping("/ToDo/v2")
@RequiredArgsConstructor
public class ToDoController {
    private final ToDoService toDoService;

    @GetMapping("")
    public List<ToDo> getAllToDos() {
        return this.toDoService.getAllToDos();
    }

    @GetMapping("/{id}")
    public ToDo getToDoById(@PathVariable("id") Long id) {
        return this.toDoService.getToDoById(id);
    }
    
    @PostMapping("")
    public ToDo createToDo(@RequestBody ToDo toDo) {
        return this.toDoService.createToDo(toDo);
    }
    
    @PutMapping("/{id}")
    public ToDo updateToDo(@PathVariable("id") Long id, @RequestBody ToDo toDo) {
        return this.toDoService.updateToDo(id, toDo);
    }

    @DeleteMapping("/{id}")
    public ToDo deleteToDo(@PathVariable("id") Long id) {
        return this.toDoService.deleteToDo(id);
    }
}