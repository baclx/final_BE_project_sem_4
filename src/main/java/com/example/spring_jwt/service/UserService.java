package com.example.spring_jwt.service;

import com.example.spring_jwt.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends GeneralService<User>, UserDetailsService {

    User findByEmail(String email);

    User getUserById(Integer id);

    void deleteUser(Integer id);


}
