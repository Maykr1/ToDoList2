package com.ToDoList2.ToDoList2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ToDoList2.ToDoList2.entity.ToDo;
import com.ToDoList2.ToDoList2.service.ToDoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@Slf4j
@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class ToDoController {
    private final ToDoService toDoService;

    @GetMapping("")
    public ResponseEntity<List<ToDo>> getAllToDos() {
        List<ToDo> toDos = this.toDoService.getAllToDos();

        return new ResponseEntity<>(toDos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDo> getToDoById(@PathVariable("id") Integer id) {
        ToDo foundToDo = this.toDoService.getToDoById(id);

        return new ResponseEntity<>(foundToDo, HttpStatus.OK);
    }
    
    @PostMapping("")
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDo toDo) {
        ToDo createdToDo = this.toDoService.createToDo(toDo);

        return new ResponseEntity<>(createdToDo, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ToDo> updateToDo(@PathVariable("id") Integer id, @RequestBody ToDo toDo) {
        ToDo updatedToDo = this.toDoService.updateToDo(id, toDo);
        
        return new ResponseEntity<>(updatedToDo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ToDo> deleteToDo(@PathVariable("id") Integer id) {
        ToDo deletedToDo = this.toDoService.deleteToDo(id);

        return new ResponseEntity<>(deletedToDo, HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteAllToDos() {
        this.toDoService.deleteAllToDos();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}