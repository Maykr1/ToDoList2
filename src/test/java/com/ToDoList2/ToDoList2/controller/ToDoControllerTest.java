package com.ToDoList2.ToDoList2.controller;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ToDoList2.ToDoList2.entity.ToDo;
import com.ToDoList2.ToDoList2.service.ToDoService;

@ExtendWith(MockitoExtension.class)
public class ToDoControllerTest {
    @Mock
    private ToDoService toDoService;
    
    @InjectMocks
    private ToDoController toDoController;

    ResponseEntity<ToDo> response;
    ToDo toDo;

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
        ResponseEntity<List<ToDo>> toDos = this.toDoController.getAllToDos();
        
        assertEquals(toDos.getStatusCode(), HttpStatus.OK);
        
        verify(toDoService).getAllToDos();
    }

    @Test
    public void testGetToDoById() {
        when(toDoService.getToDoById(anyInt())).thenReturn(toDo);

        response = toDoController.getToDoById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(toDo, response.getBody());
        verify(toDoService).getToDoById(anyInt());
    }

    @Test
    public void testCreateToDo() {
        when(toDoService.createToDo(toDo)).thenReturn(toDo);

        response = toDoController.createToDo(toDo);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(toDo, response.getBody());
        verify(toDoService).createToDo(toDo);
    }

    @Test
    public void testUpdateToDo() {
        toDo.setCompleted(true);
        when(toDoService.updateToDo(1, toDo)).thenReturn(toDo);

        response = toDoController.updateToDo(1, toDo);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(toDo, response.getBody());
        assertEquals(toDo.getCompleted(), true);
        verify(toDoService).updateToDo(1, toDo);
    }

    @Test
    public void testDeleteToDo() {
        when(toDoService.deleteToDo(anyInt())).thenReturn(toDo);
        
        response = toDoController.deleteToDo(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(toDo, response.getBody());
        verify(toDoService).deleteToDo(anyInt());
    }

    @Test
    public void testDeleteAllToDos() {
        ResponseEntity<Void> responseDeleteAllToDos = toDoController.deleteAllToDos();
        
        assertEquals(HttpStatus.NO_CONTENT, responseDeleteAllToDos.getStatusCode());
        assertNull(responseDeleteAllToDos.getBody());
        verify(toDoService).deleteAllToDos();
    }
}
