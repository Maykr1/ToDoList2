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
        List<ToDo> toDos = this.toDoRepository.findAll();
        
        return toDos;
    }

    @Override
    public ToDo getToDoById(Integer id) {
        Optional<ToDo> toDo = this.toDoRepository.findById(id);
        if (!toDo.isPresent()) {
            throw new ResourceNotFoundException("ToDo not found");
        }

        return toDo.get();
    }

    @Override
    public ToDo createToDo(ToDo toDo) {
        return this.toDoRepository.save(toDo);
    }

    @Override
    public ToDo updateToDo(Integer id, ToDo toDo) {
        Optional<ToDo> toDoToUpdateOptional = this.toDoRepository.findById(id);
        if(!toDoToUpdateOptional.isPresent()) {
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

        return this.toDoRepository.save(toDoToUpdate);
    }

        @Override
    public ToDo deleteToDo(Integer id) {
        Optional<ToDo> toDoToDeleteOptional = this.toDoRepository.findById(id);

        if (!toDoToDeleteOptional.isPresent()) {
            throw new ResourceNotFoundException("ToDo not found");
        }

        ToDo toDoToDelete = toDoToDeleteOptional.get();
        this.toDoRepository.delete(toDoToDelete);
        return toDoToDelete;
    }

    @Override
    public void deleteAllToDos() {
        this.toDoRepository.deleteAll();
    }
}
