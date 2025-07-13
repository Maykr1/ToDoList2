package com.ToDoList2.ToDoList2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ToDoList2.ToDoList2.entity.CustomUser;
import com.ToDoList2.ToDoList2.service.CustomUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ToDo/v2")
public class CustomUserController {
    private final CustomUserService customUserService;

    @PostMapping("/createuser")
    public ResponseEntity<String> createUser(@RequestBody @Valid CustomUser user) {
        CustomUser newUser = this.customUserService.createUser(user);

        return new ResponseEntity<>("New User Created: " + newUser.toString(), HttpStatus.CREATED);
    }
}
