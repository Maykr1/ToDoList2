package com.ToDoList2.ToDoList2.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.ToDoList2.ToDoList2.entity.ToDo;
import com.ToDoList2.ToDoList2.exception.ResourceNotFoundException;
import com.ToDoList2.ToDoList2.service.ToDoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ToDoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ToDoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ToDoService toDoService;

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
    public void testGetAllToDos() throws JsonProcessingException, Exception {
        List<ToDo> allToDos = new ArrayList<>();
        allToDos.add(toDo);
        
        when(toDoService.getAllToDos()).thenReturn(allToDos);
        
        mockMvc.perform(get("/ToDo/v2"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(allToDos)));
        
        verify(toDoService).getAllToDos();
    }

    @Test
    public void testGetToDoById() throws JsonProcessingException, Exception {
        when(toDoService.getToDoById(1)).thenReturn(toDo);

        mockMvc.perform(get("/ToDo/v2/1"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(toDo)));

        verify(toDoService).getToDoById(1);
    }

    @Test
    public void testGetToDoByIdNull() throws JsonProcessingException, Exception {
        when(toDoService.getToDoById(1))
            .thenThrow(new ResourceNotFoundException("ToDo not found"));
        
        mockMvc.perform(get("/ToDo/v2/1"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("ToDo not found"));

        verify(toDoService).getToDoById(1);
    }

    @Test
    public void testCreateToDo() throws JsonProcessingException, Exception {
        when(toDoService.createToDo(toDo)).thenReturn(toDo);

        mockMvc.perform(post("/ToDo/v2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDo)))
            .andExpect(status().isCreated())
            .andExpect(content().string(objectMapper.writeValueAsString(toDo)));

        verify(toDoService).createToDo(toDo);
    }

    @Test
    public void testUpdateToDo() throws JsonProcessingException, Exception {
        toDo.setCompleted(true);

        when(toDoService.updateToDo(1, toDo)).thenReturn(toDo);
                
        mockMvc.perform(put("/ToDo/v2/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDo)))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(toDo)));
        
        verify(toDoService).updateToDo(1, toDo);
    }

    @Test
    public void testUpdateToDoNotFound() throws JsonProcessingException, Exception {
        toDo.setId(1234);

        when(toDoService.updateToDo(1, toDo))
            .thenThrow(new ResourceNotFoundException("ToDo not found"));

        mockMvc.perform(put("/ToDo/v2/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDo)))
            .andExpect(status().isNotFound())
            .andExpect(content().string("ToDo not found"));

        verify(toDoService).updateToDo(1, toDo);
    }

    @Test
    public void testDeleteToDo() throws JsonProcessingException, Exception {
        when(toDoService.deleteToDo(1)).thenReturn(toDo);

        mockMvc.perform(delete("/ToDo/v2/1"))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(toDo)));
        
        verify(toDoService).deleteToDo(1);
    }

    @Test
    public void testDeleteToDoNotFound() throws JsonProcessingException, Exception {
        toDo.setId(1231241231);

        when(toDoService.deleteToDo(1))
            .thenThrow(new ResourceNotFoundException("ToDo not found"));

        mockMvc.perform(delete("/ToDo/v2/1"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("ToDo not found"));
        
        verify(toDoService).deleteToDo(1);
    }

    @Test
    public void testDeleteAllToDos() throws JsonProcessingException, Exception {
        mockMvc.perform(delete("/ToDo/v2"))
            .andExpect(status().isNoContent());
    }
}
