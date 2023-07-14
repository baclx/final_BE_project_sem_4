package com.example.spring_jwt.service;

import com.example.spring_jwt.entities.Role;

public interface RoleService extends GeneralService<Role> {
    Role findByName(String name);
}
