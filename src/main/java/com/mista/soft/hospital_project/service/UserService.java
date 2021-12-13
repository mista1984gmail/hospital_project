package com.mista.soft.hospital_project.service;

import com.mista.soft.hospital_project.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    User findUserById(Integer id);

    List<User> allUsers();

    boolean saveUser(User appUser);

    void update(User user);

    boolean deleteUser(Integer id);

    User findByUsername(String username);

}
