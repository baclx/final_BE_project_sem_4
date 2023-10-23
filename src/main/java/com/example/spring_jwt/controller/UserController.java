package com.example.spring_jwt.controller;

import com.example.spring_jwt.entities.User;
import com.example.spring_jwt.model.request.UpdateUser;
import com.example.spring_jwt.service.RoleService;
import com.example.spring_jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    public String hello() {
        return "hello user";
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Integer id, @RequestBody UpdateUser user){
        User oldUser = userService.getUserById(id);
        if (oldUser == null){
            return ResponseEntity.notFound().build();
        }else{
            oldUser.setFullName(user.getFullName());
            oldUser.setEmail(user.getEmail());

            return ResponseEntity.ok(oldUser);
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Integer id){
        User deleteUser = userService.getUserById(id);
        if (deleteUser == null){
            return ResponseEntity.notFound().build();
        }else{
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        }
    }
}
