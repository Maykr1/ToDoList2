package com.ToDoList2.ToDoList2.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ToDoList2.ToDoList2.entity.ToDo;
import com.ToDoList2.ToDoList2.exception.ResourceNotFoundException;
import com.ToDoList2.ToDoList2.repository.ToDoRepository;
import com.ToDoList2.ToDoList2.service.ToDoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {
    private final ToDoRepository toDoRepository;

    @Override
    public List<ToDo> getAllToDos() {
        log.info("[ GET ALL TODOs ] - Inside getAllToDos method");
        List<ToDo> toDos = this.toDoRepository.findAll();

        log.info("[ GET ALL TODOs ] - Finished getAllToDos method");
        return toDos;
    }

    @Override
    public ToDo getToDoById(Integer id) {
        log.info("[ GET TODO BY ID ] - Inside getToDoById method");
        Optional<ToDo> toDo = this.toDoRepository.findById(id);
        
        if (!toDo.isPresent()) {
            log.error("[ GET TODO BY ID] - No ToDo found correspoding with id: {}", id);
            throw new ResourceNotFoundException("ToDo not found");
        }

        ToDo toDoToGet = toDo.get();

        log.info("[ GET TODO BY ID ] - Finished getToDoById method");
        return toDoToGet;
    }

    @Override
    public ToDo createToDo(ToDo toDo) {
        log.info("[ CREATE TO DO ] - Inside createToDo method");
        ToDo createdProduct = this.toDoRepository.save(toDo);

        log.info("[ CREATE TO DO ] - Finished createToDo method");
        return createdProduct;
    }

    @Override
    public ToDo updateToDo(Integer id, ToDo toDo) {
        log.info("[ UPDATE TO DO ] - Inside updateToDo method");
        Optional<ToDo> toDoToUpdateOptional = this.toDoRepository.findById(id);

        if(!toDoToUpdateOptional.isPresent()) {
            log.error("[ GET TODO BY ID] - No ToDo found correspoding with id: {}", id);
            throw new ResourceNotFoundException("ToDo not found");
        }

        ToDo toDoToUpdate = toDoToUpdateOptional.get();

        if (toDo.getTitle() != null) {
            toDoToUpdate.setTitle(toDo.getTitle());
        }
        if (toDo.getDescription() != null) {
            toDoToUpdate.setDescription(toDo.getDescription());
        }
        if (toDo.getDueDate() != null) {
            toDoToUpdate.setDueDate(toDo.getDueDate());
        }
        if (toDo.getCompleted() != null) {
            toDoToUpdate.setCompleted(toDo.getCompleted());
        }

        log.info("[ UPDATE TO DO ] - Finished updateToDo method");
        return this.toDoRepository.save(toDoToUpdate);
    }

    @Override
    public ToDo deleteToDo(Integer id) {
        log.info("[ DELETE TO DO ] - Inside deleteToDo method");
        Optional<ToDo> toDoToDeleteOptional = this.toDoRepository.findById(id);

        if (!toDoToDeleteOptional.isPresent()) {
            throw new ResourceNotFoundException("ToDo not found");
        }

        ToDo toDoToDelete = toDoToDeleteOptional.get();

        this.toDoRepository.delete(toDoToDelete);

        log.info("[ DELETE TO DO ] - Finished deleteToDo method");
        return toDoToDelete;
    }

    @Override
    public void deleteAllToDos() {
        log.info("[ DELETE ALL TODOs ] - Inside deleteAllToDos method");
        
        this.toDoRepository.deleteAll();

        log.info("[ DELETE ALL TODOs ] - Inside deleteAllToDos method");
    }
}
