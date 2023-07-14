package com.example.spring_jwt.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Secured("ROLE_ADMIN")
    @GetMapping("/hello")
    public String hello(){
        return "hello admin";
    }
}
