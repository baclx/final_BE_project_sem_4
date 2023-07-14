package com.example.spring_jwt.service.impl;

import com.example.spring_jwt.entities.Role;
import com.example.spring_jwt.repository.RoleRepository;
import com.example.spring_jwt.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(Integer id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void remove(Integer id) {
        roleRepository.deleteById(id);

    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
