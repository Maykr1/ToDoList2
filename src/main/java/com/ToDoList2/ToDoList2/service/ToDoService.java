package com.ToDoList2.ToDoList2.service;

import java.util.List;

import com.ToDoList2.ToDoList2.entity.ToDo;

public interface ToDoService {
    public List<ToDo> getAllToDos();
    public ToDo getToDoById(Integer id);
    public ToDo createToDo(ToDo toDo);
    public ToDo updateToDo(Integer id, ToDo toDo);
    public ToDo deleteToDo(Integer id);
    public void deleteAllToDos();
}
