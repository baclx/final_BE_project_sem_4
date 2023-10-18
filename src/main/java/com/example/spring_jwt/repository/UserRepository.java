package com.example.spring_jwt.repository;

import com.example.spring_jwt.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //Optional<User> findByEmail(String email);
    List<User> findByEmail(String email);

}
