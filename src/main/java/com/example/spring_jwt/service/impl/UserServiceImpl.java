package com.example.spring_jwt.service.impl;

import com.example.spring_jwt.entities.User;
import com.example.spring_jwt.jwt.UserPrinciple;
import com.example.spring_jwt.repository.UserRepository;
import com.example.spring_jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void remove(Integer id) {
        userRepository.deleteById(id);

    }

    @Override
    public User findByEmail(String email) {
        if (userRepository.findByEmail(email).size() != 1) {
            return null;
        }
        return userRepository.findByEmail(email).get(0);
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAllByRole(String role) {
        return userRepository.findAllByRoles(role);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userOptional = userRepository.findByEmail(username).get(0);
//        if (!userOptional.isPresent()){
//            throw  new UsernameNotFoundException(username);
//        }
        return UserPrinciple.build(userOptional);
    }
}
