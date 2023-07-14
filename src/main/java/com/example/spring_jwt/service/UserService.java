package com.example.spring_jwt.service;

import com.example.spring_jwt.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends GeneralService<User> , UserDetailsService {
    Optional<User> findByUsername(String username);
}
