package com.ToDoList2.ToDoList2.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.ToDoList2.ToDoList2.entity.CustomUser;
import com.ToDoList2.ToDoList2.service.CustomUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CustomUserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CustomUserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired 
    private ObjectMapper objectMapper;

    @MockitoBean
    private CustomUserService customUserService;

    ResponseEntity<String> response;
    CustomUser customUser;


    @BeforeEach
    public void setup() {
        customUser = CustomUser.builder()
            .id(1)
            .username("testUsername")
            .password("testPassword")
            .todoList(new ArrayList<>())
            .build();
    }

    @Test
    public void createUser() throws JsonProcessingException, Exception {
        when(customUserService.createUser(any(CustomUser.class))).thenReturn(customUser);

        mockMvc.perform(post("/ToDo/v2/createuser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customUser)))
            .andExpect(status().isCreated());

        verify(customUserService).createUser(customUser);
    }

    @Test
    public void createUserAlreadyExists() throws JsonProcessingException, Exception {
        when(customUserService.createUser(any(CustomUser.class)))
            .thenThrow(new UsernameNotFoundException("User does not exist"));

        mockMvc.perform(post("/ToDo/v2/createuser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customUser)))
            .andExpect(status().isNotFound())
            .andExpect(content().string("User does not exist"));

        verify(customUserService).createUser(customUser);
    }
}
