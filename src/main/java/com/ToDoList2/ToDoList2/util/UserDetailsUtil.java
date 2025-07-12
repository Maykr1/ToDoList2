package com.ToDoList2.ToDoList2.util;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ToDoList2.ToDoList2.entity.CustomUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserDetailsUtil implements UserDetails {
    private final CustomUser customUser;

    public CustomUser getCustomUser() {
        return this.customUser;
    }
    
    @Override
    public String getUsername() {
        return customUser.getUsername();
    }

    @Override
    public String getPassword() {
        return customUser.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
