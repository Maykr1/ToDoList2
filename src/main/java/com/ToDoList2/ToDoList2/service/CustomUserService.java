package com.ToDoList2.ToDoList2.service;

import com.ToDoList2.ToDoList2.entity.CustomUser;

public interface CustomUserService {
    public CustomUser getCustomUserByUsername(String username);
    public CustomUser createUser(CustomUser user);
}
