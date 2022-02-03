package com.mista.soft.hospital_project.service.impl;

import com.mista.soft.hospital_project.exceptions.IdIsNotFoundOnDbException;
import com.mista.soft.hospital_project.exceptions.UserNameNotFoundException;
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
import org.springframework.transaction.annotation.Transactional;
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
            throw new UserNameNotFoundException();
        }
        log.info("User "+ username + " found.");
        return user;
    }

    @Override
    public User findUserById(Integer id) {
        Optional<User> userFromDb= userRepository.findById(id);
        if(userFromDb==null){
            throw new IdIsNotFoundOnDbException(id);
        }

        log.info("User by id: " + id + " found.");
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
        log.info("Find all users with role USER");
        return patientsList;
    }

    @Override
    @Transactional
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            log.info("User " + user.getFirstName() +", " + user.getLastName() + " (" + user.getId() + ")"
                    +  " is exists.");
            return false;
        }
        user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(false);
        userRepository.save(user);

        log.info("User " + user.getFirstName() +", " + user.getLastName() + " (" + user.getId() + ")"
                +  " saved.");

        if(!StringUtils.isEmpty(user.getEmail())){
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Hospital. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getFirstName(),
                    user.getActivationCode()
            );
            sendEmailService.send(user.getEmail(),"Activation code", message);
            log.info("User " + user.getFirstName() +", " + user.getLastName()
                    + " (" + user.getId() + ")" +  " an activation code has been sent.");
        }
        return true;
    }

    @Override
    @Transactional
    public void update(User user) {
        user.setActive(true);
        user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
        userRepository.save(user);
        log.info("User " + user.getFirstName() +", " + user.getLastName()
                + " (" + user.getId() + ")" +  " updated.");
    }

    @Override
    @Transactional
    public void updateAdmin(User user, String[] detailIDs, String[] detailNames) {

        for(int i = 0; i < detailNames.length; i++){
            if(detailIDs != null && detailIDs.length > 0){
                String role = detailNames[i];
                switch (role){
                    case "ROLE_USER":
                        user.setRoles(Collections.singleton(new Role(1, detailNames[i])));
                        break;
                    case "ROLE_ADMIN":
                        user.setRoles(Collections.singleton(new Role(2, detailNames[i])));
                        break;
                    case "ROLE_DOCTOR":
                        user.setRoles(Collections.singleton(new Role(3, detailNames[i])));
                        break;
                    case "ROLE_NURSE":
                        user.setRoles(Collections.singleton(new Role(4, detailNames[i])));
                        break;
                }
            }

        }
        userRepository.save(user);

        log.info("User " + user.getFirstName() +", " + user.getLastName()
                + " (" + user.getId() + ")" +  " updated.");
    }


    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNameNotFoundException();
        }
        log.info("User "+ username +  " found.");
        return user;
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

        log.info("User " + user.getFirstName() +", " + user.getLastName()
                + " (" + user.getId() + ")" + " activated.");
        return true;
    }
}
