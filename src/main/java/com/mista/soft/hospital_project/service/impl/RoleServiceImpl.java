package com.mista.soft.hospital_project.service.impl;

import com.mista.soft.hospital_project.model.entity.User;
import com.mista.soft.hospital_project.model.repository.RoleRepository;
import com.mista.soft.hospital_project.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mista.soft.hospital_project.model.entity.Role;


import java.util.*;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> allRoles() {
        log.info("Find all roles");
        return roleRepository.findAll();
    }

    @Override
    public List<Role> rolesUserToList(User user) {
        Set<Role> listRoles = new HashSet<>();
        listRoles=user.getRoles();
        List<Role>roles= new ArrayList<>();
        Iterator<Role> iterator = listRoles.iterator();
        while (iterator.hasNext()) {
            Role role = iterator.next();
            roles.add(role);
        }
        return roles;
    }
}
