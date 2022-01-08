package com.mista.soft.hospital_project.service.impl;

import com.mista.soft.hospital_project.model.entity.User;
import com.mista.soft.hospital_project.model.repository.UserRepository;
import com.mista.soft.hospital_project.service.UserService;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.mista.soft.hospital_project.model.entity.Role;
import org.thymeleaf.util.StringUtils;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    SendEmailService sendEmailService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    @Override
    public User findUserById(Integer id) {
        Optional<User> userFromDb = userRepository.findById(id);
        return userFromDb.orElse(new User());
    }

    @Override
    public List<User> allUsers() {
        log.info("Find all users");
        return userRepository.findAll();
    }

    @Override
    public List<User> allUsersWithRoleUser() {
        List<User> users = userRepository.findAll();
        List<User> patientsList = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getAuthorities().toString().contains("ROLE_USER")) {
                patientsList.add(users.get(i));
            }
        }
        return patientsList;
    }

    @Override
    public boolean saveUser(User user) {

        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(false);
        userRepository.save(user);

        if(!StringUtils.isEmpty(user.getEmail())){
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Hospital. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getFirstName(),
                    user.getActivationCode()
            );
            sendEmailService.send(user.getEmail(),"Activation code", message);
        }

        return true;
    }

    @Override
    public void update(User user) {
        user.setActive(true);
        user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
        userRepository.save(user);
    }

    @Override
    public void updateAdmin(User user) {
        userRepository.save(user);
    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null){
            return false;
        }
        user.setActivationCode(null);
        user.setActive(true);
        userRepository.save(user);

        return true;
    }
}
