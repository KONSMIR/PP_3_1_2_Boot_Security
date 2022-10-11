package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.*;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Collection<Role> getAllRoles() {
        Collection<Role> roles = roleRepository.findAll();
        return roles;
    }

    @Override
    public Collection<Role> getByName(String name) {
        Collection<Role> roles = new ArrayList<>();
        for (Role role : getAllRoles()) {
            if (name.contains(role.getName()))
                roles.add(role);
        }
        return roles;
    }
}
