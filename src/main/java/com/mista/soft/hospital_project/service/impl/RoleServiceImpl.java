package com.mista.soft.hospital_project.service.impl;

import com.mista.soft.hospital_project.model.repository.RoleRepository;
import com.mista.soft.hospital_project.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mista.soft.hospital_project.model.entity.Role;


import java.util.List;

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
}
