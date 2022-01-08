package com.mista.soft.hospital_project.service;

import com.mista.soft.hospital_project.model.entity.Role;
import com.mista.soft.hospital_project.model.entity.User;
import com.mista.soft.hospital_project.model.repository.UserRepository;
import com.mista.soft.hospital_project.service.impl.SendEmailService;
import com.mista.soft.hospital_project.util.UserFixture;
import lombok.extern.slf4j.Slf4j;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private SendEmailService mailSender;

    @Test
    public void saveUserTest() throws Exception {
        // GIVEN
        User user = UserFixture.createUser();

        // WHEN
        boolean isUserSaved = userService.saveUser(user);

        // THEN
        Assert.assertTrue(isUserSaved);
        Assert.assertNotNull(user.getActivationCode());
        Assert.assertTrue(CoreMatchers.is(user.getRoles())
                .matches(Collections.singleton(new Role(1, "ROLE_USER"))));
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
        Mockito.verify(mailSender, Mockito.times(1))
                .send(
                        ArgumentMatchers.eq(user.getEmail()),
                        ArgumentMatchers.eq("Activation code"),
                        ArgumentMatchers.contains("Welcome to Hospital."));
    }
    @Test
    public void saveUserFailTest(){
        // GIVEN
        User user = UserFixture.createUser();
        Mockito.doReturn(new User())
                .when(userRepo)
                .findByUsername("login");

        // WHEN
        boolean isUserSaved = userService.saveUser(user);

        // THEN
        Assert.assertFalse(isUserSaved);
        Mockito.verify(userRepo, Mockito.times(0)).save(any(User.class));
        Mockito.verify(mailSender, Mockito.times(0))
                .send(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString());
    }

    @Test
    public void activateUserTest(){
        // GIVEN
        User user = new User();
        user.setActivationCode("12345");
        Mockito.doReturn(user)
                .when(userRepo)
                .findByActivationCode("activate");
        // WHEN
        boolean isUserActivated = userService.activateUser("activate");
        // THEN
        Assert.assertTrue(isUserActivated);
        Assert.assertNull(user.getActivationCode());
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }

    @Test
    public void activateUserFailTest(){
        // GIVEN

        // WHEN
        boolean isUserActivated = userService.activateUser("activate");
        // THEN
        Assert.assertFalse(isUserActivated);
        Mockito.verify(userRepo, Mockito.times(0)).save(any(User.class));
    }
    @Test
    public void findByUsernameTest(){
        // GIVEN
        User user = UserFixture.createUser();
        Mockito.doReturn(user)
                .when(userRepo)
                .findByUsername("login");
        // WHEN
        boolean isUserSaved = userService.saveUser(user);
        User userFromTest = userService.findByUsername("login");

        // THEN
        Assert.assertFalse(isUserSaved);
        Assert.assertTrue(CoreMatchers.is(userFromTest.getUsername())
                .matches("login"));

    }

    @Test
    public void allUsersTest(){
        // GIVEN
        User user1 = UserFixture.createUser();
        User user2 = UserFixture.createUser();
        user2.setUsername("login2");
        User user3 = UserFixture.createUser();
        user3.setUsername("login3");
        List<User> usersList = new ArrayList<>();
        usersList.add(user1);
        usersList.add(user2);
        usersList.add(user3);
        Mockito.doReturn(usersList)
                .when(userRepo)
                .findAll();

        // WHEN
        boolean isUserSaved1 = userService.saveUser(user1);
        boolean isUserSaved2 = userService.saveUser(user2);
        boolean isUserSaved3 = userService.saveUser(user3);
        List<User> users = userService.allUsers();

        // THEN
        Assert.assertTrue(CoreMatchers.is(users.size())
                .matches(3));

    }

    @Test
    public void updateTest(){
        // GIVEN
        User userForUpdate = UserFixture.createUser();
        Mockito.doReturn(userForUpdate)
                .when(userRepo)
                .save(userForUpdate);
        // WHEN
        userService.update(userForUpdate);
        // THEN
        Assert.assertNotNull(userForUpdate);
        Mockito.verify(userRepo, Mockito.times(1)).save(any(User.class));
        Assert.assertSame(userForUpdate.getFirstName(),"Ivan");
        Assert.assertSame(userForUpdate.getLastName(),"Ivanov");
    }


}

