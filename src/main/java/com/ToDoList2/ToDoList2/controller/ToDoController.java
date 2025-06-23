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
@RequestMapping("/ToDo/v2")
@RequiredArgsConstructor
public class ToDoController {
    private final ToDoService toDoService;

    @GetMapping("")
    public ResponseEntity<List<ToDo>> getAllToDos() {
        log.info("[ GET ALL TODOs ] - Inside getAllToDos method call");
        List<ToDo> toDos = this.toDoService.getAllToDos();

        log.info("[ GET ALL TODOs ] - Finished getAllToDos method call");
        return new ResponseEntity<>(toDos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDo> getToDoById(@PathVariable("id") Integer id) {
        log.info("[ GET TODO BY ID ] - Inside getToDoById method call");
        ToDo foundToDo = this.toDoService.getToDoById(id);

        log.info("[ GET TODO BY ID ] - Finished getToDoById method call");
        return new ResponseEntity<>(foundToDo, HttpStatus.OK);
    }
    
    @PostMapping("")
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDo toDo) {
        log.info("[ CREATE TODO ] - Inside createToDo method call");
        ToDo createdToDo = this.toDoService.createToDo(toDo);

        log.info("[ CREATE TODO ] - Finished createToDo method call");
        return new ResponseEntity<>(createdToDo, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ToDo> updateToDo(@PathVariable("id") Integer id, @RequestBody ToDo toDo) {
        log.info("[ UPDATE TODO ] - Inside updateToDo method call");
        ToDo updatedToDo = this.toDoService.updateToDo(id, toDo);
        
        log.info("[ UPDATE TODO ] - Finished updateToDo method call");
        return new ResponseEntity<>(updatedToDo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ToDo> deleteToDo(@PathVariable("id") Integer id) {
        log.info("[ DELETE TODO ] - Inside deleteToDo method call");
        ToDo deletedToDo = this.toDoService.deleteToDo(id);

        log.info("[ DELETE TODO ] - Finished deleteToDo method call");
        return new ResponseEntity<>(deletedToDo, HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteAllToDos() {
        log.info("[ DELETE ALL TODOs ] - Inside deleteAllToDos method call");
        this.toDoService.deleteAllToDos();

        log.info("[ DELETE ALL TODOs ] - Finished deleteAllToDos method call");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}