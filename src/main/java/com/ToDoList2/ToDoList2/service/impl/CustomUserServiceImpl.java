package com.ToDoList2.ToDoList2.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ToDoList2.ToDoList2.entity.CustomUser;
import com.ToDoList2.ToDoList2.repository.CustomUserRepository;
import com.ToDoList2.ToDoList2.service.CustomUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserServiceImpl implements CustomUserService, UserDetailsService {
    private final PasswordEncoder encoder;
    private final CustomUserRepository customUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("[ GET USER BY USERNAME ] - Inside loadUserByUsername method");
        
        CustomUser foundUser = this.customUserRepository.findByUsername(username);
        
        if (foundUser == null) {
            log.error("[ GET USER BY USERNAME ] - User with username: {} does not exist", username);
            throw new UsernameNotFoundException("User does not exist");
        }

        log.info("[ GET USER BY USERNAME ] - Finished loadUserByUsername method");
        return User
            .withUsername(foundUser.getUsername())
            .password(foundUser.getPassword())
            .build();
    }

    @Override
    public CustomUser createUser(CustomUser user) {
        log.info("[ CREATE NEW USER ] - Inside createNewUser method");
        CustomUser userAlreadyExists = this.customUserRepository.findByUsername(user.getUsername());

        if (userAlreadyExists != null) {
            log.error("[ CREATE NEW USER ] - User already exists corresponding to username:", user.getUsername());
            throw new UserAlreadyExistsException("User already exists");
        }

        user.setPassword(encoder.encode(user.getPassword()));

        log.info("[ CREATE NEW USER ] - Finished createNewUser method");
        return customUserRepository.save(user);
    }
}
