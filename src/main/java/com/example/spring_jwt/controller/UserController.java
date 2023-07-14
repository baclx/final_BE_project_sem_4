package com.example.spring_jwt.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/hello")
    public String hello(){
        return "hello user";
    }
}
