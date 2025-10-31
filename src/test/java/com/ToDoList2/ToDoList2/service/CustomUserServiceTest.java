package com.ToDoList2.ToDoList2.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ToDoList2.ToDoList2.entity.CustomUser;
import com.ToDoList2.ToDoList2.exception.UserAlreadyExistsException;
import com.ToDoList2.ToDoList2.repository.CustomUserRepository;
import com.ToDoList2.ToDoList2.service.impl.CustomUserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CustomUserServiceTest {
    @Mock
    private CustomUserRepository customUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomUserServiceImpl customUserServiceImpl;

    CustomUser customUser;
    CustomUser createdUser;

    @BeforeEach
    public void setup() {
        customUser = CustomUser.builder()
            .id(1)
            .username("testUsername")
            .password("testPassword")
            .build();
    }

    @Test
    public void loadUserByUsername() {
        when(customUserRepository.findByUsername(customUser.getUsername())).thenReturn(customUser);

        UserDetails userDetails = customUserServiceImpl.loadUserByUsername(customUser.getUsername());

        assertNotNull(userDetails);
        assertEquals(customUser.getUsername(), userDetails.getUsername());
        assertEquals(customUser.getPassword(), userDetails.getPassword());

        verify(customUserRepository).findByUsername(customUser.getUsername());
    }

    @Test
    public void loadUserByUsernameNull() {
        when(customUserRepository.findByUsername(customUser.getUsername())).thenReturn(null);

        assertThrows(
            BadCredentialsException.class, 
            () -> customUserServiceImpl.loadUserByUsername(customUser.getUsername())
        );

        verify(customUserRepository).findByUsername(customUser.getUsername());
    }

    @Test
    public void loadCustomUserByUsername() {
        when(customUserRepository.findByUsername(customUser.getUsername())).thenReturn(customUser);

        CustomUser foundUser = customUserServiceImpl.getCustomUserByUsername(customUser.getUsername());

        assertNotNull(foundUser);
        assertEquals(customUser.getUsername(), foundUser.getUsername());
        assertEquals(customUser.getPassword(), foundUser.getPassword());

        verify(customUserRepository).findByUsername(customUser.getUsername());
    }

    @Test
    public void loadCustomUserByUsernameNull() {
        when(customUserRepository.findByUsername(customUser.getUsername())).thenReturn(null);

        assertThrows(
            BadCredentialsException.class, 
            () -> customUserServiceImpl.getCustomUserByUsername(customUser.getUsername())
        );

        verify(customUserRepository).findByUsername(customUser.getUsername());
    }

    @Test
    public void createUser() {
        when(customUserRepository.findByUsername(customUser.getUsername())).thenReturn(null);
        when(passwordEncoder.encode(customUser.getPassword())).thenReturn("encodedPassword");
        when(customUserRepository.save(customUser)).thenReturn(customUser);

        createdUser = customUserServiceImpl.createUser(customUser);

        assertEquals(customUser.getUsername(), createdUser.getUsername());
        assertEquals("encodedPassword", createdUser.getPassword());

        verify(customUserRepository).findByUsername(customUser.getUsername());
        verify(customUserRepository).save(customUser);
    }

    @Test
    public void createUserAlreadyExists() {
        when(customUserRepository.findByUsername(customUser.getUsername())).thenReturn(customUser);

        assertThrows(
            UserAlreadyExistsException.class, 
            () -> customUserServiceImpl.createUser(customUser)
        );

        verify(customUserRepository).findByUsername(customUser.getUsername());
        verify(customUserRepository, never()).save(customUser);
    }
}
