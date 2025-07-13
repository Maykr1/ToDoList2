package com.ToDoList2.ToDoList2.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ToDoList2.ToDoList2.entity.ToDo;
import com.ToDoList2.ToDoList2.exception.ResourceNotFoundException;
import com.ToDoList2.ToDoList2.repository.ToDoRepository;
import com.ToDoList2.ToDoList2.service.impl.ToDoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ToDoServiceTest {
    @Mock
    private ToDoRepository toDoRepository;

    @InjectMocks
    private ToDoServiceImpl toDoServiceImpl;

    ToDo toDo;
    ToDo response;

    @BeforeEach
    public void setup() {
        toDo = ToDo.builder()
            .id(1)
            .title("example")
            .description("example")
            .dueDate(LocalDate.of(2025, 6, 1))
            .completed(false)
            .build();
    }

    @Test
    public void testGetAllToDos() {
        List<ToDo> toDos = this.toDoServiceImpl.getAllToDos();

        assertNotNull(toDos);
        verify(toDoRepository).findAll();
    }

    @Test
    public void testGetToDoById() {
        when(toDoRepository.findById(toDo.getId())).thenReturn(Optional.of(toDo));

        response = toDoServiceImpl.getToDoById(toDo.getId());

        assertEquals(toDo, response);
        verify(toDoRepository).findById(toDo.getId());
    }

    @Test
    public void testGetToDoByIdNull() {
        toDo.setId(123);
        
        when(toDoRepository.findById(anyInt())).thenReturn(Optional.empty());

        ResourceNotFoundException e = assertThrows(
            ResourceNotFoundException.class, 
            () -> toDoServiceImpl.getToDoById(1)
        );

        assertEquals(e.getMessage(), "ToDo not found");
        verify(toDoRepository).findById(1);
    }

    @Test
    public void testCreateToDo() {
        when(toDoRepository.save(toDo)).thenReturn(toDo);

        response = toDoServiceImpl.createToDo(toDo);

        assertEquals(toDo, response);
        verify(toDoRepository).save(toDo);
    }

    @Test
    public void testUpdateToDo() {
        toDo.setCompleted(true);

        when(toDoRepository.findById(toDo.getId())).thenReturn(Optional.of(toDo));
        when(toDoRepository.save(toDo)).thenReturn(toDo);

        response = toDoServiceImpl.updateToDo(toDo.getId(), toDo);
        
        assertEquals(toDo, response);
        assertEquals(toDo.getCompleted(), true);
        verify(toDoRepository).findById(1);
        verify(toDoRepository).save(toDo);
    }

    @Test
    public void testUpdateToDoNull() {
        toDo.setId(123);
        toDo.setCompleted(true);
        
        when(toDoRepository.findById(anyInt())).thenReturn(Optional.empty());

        ResourceNotFoundException e = assertThrows(
            ResourceNotFoundException.class, 
            () -> toDoServiceImpl.updateToDo(1, toDo)
        );

        assertEquals(e.getMessage(), "ToDo not found");
        verify(toDoRepository).findById(1);
        verify(toDoRepository, never()).save(toDo);
    }

    @Test
    public void testDeleteToDo() {
        when(toDoRepository.findById(toDo.getId())).thenReturn(Optional.of(toDo));

        response = toDoServiceImpl.deleteToDo(toDo.getId());

        assertEquals(response, toDo);
        verify(toDoRepository).findById(1);
        verify(toDoRepository).delete(toDo);
    }

    @Test
    public void testDeleteToDoNull() {
        toDo.setId(123);
        
        when(toDoRepository.findById(anyInt())).thenReturn(Optional.empty());

        ResourceNotFoundException e = assertThrows(
            ResourceNotFoundException.class, 
            () -> toDoServiceImpl.deleteToDo(1)
        );

        assertEquals(e.getMessage(), "ToDo not found");
        verify(toDoRepository).findById(1);
        verify(toDoRepository, never()).delete(toDo);
    }

    @Test
    public void testDeleteAllToDos() {
        toDoServiceImpl.deleteAllToDos();

        verify(toDoRepository).deleteAll();
    }
}
