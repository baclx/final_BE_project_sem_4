package com.example.spring_jwt.service;

import com.example.spring_jwt.entities.Role;

import java.util.List;

public interface RoleService extends GeneralService<Role> {
    Role findByName(String name);

    List<Role> getAll();
}
