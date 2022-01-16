package com.mista.soft.hospital_project.service;

import com.mista.soft.hospital_project.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.mista.soft.hospital_project.model.entity.Role;


import java.util.List;

public interface RoleService {
    List<Role> allRoles();

    List<Role> rolesUserToList(User user);
}
