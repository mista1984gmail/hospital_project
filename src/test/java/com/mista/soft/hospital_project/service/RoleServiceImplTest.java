package com.mista.soft.hospital_project.service;

import com.mista.soft.hospital_project.model.entity.Role;
import com.mista.soft.hospital_project.model.entity.User;
import com.mista.soft.hospital_project.model.repository.RoleRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleServiceImplTest {

    @Autowired
    private RoleService roleService;

    @MockBean
    private RoleRepository roleRepository;

    @Test
    public void findAllTest(){
        // GIVEN
        Role role1 = new Role();
        role1.setId(1);
        role1.setName("USER");
        Set<User> userSet1 =new HashSet<>();
        role1.setUsers(userSet1);

        Role role2 = new Role();
        role2.setId(1);
        role2.setName("USER");
        Set<User> userSet2 =new HashSet<>();
        role2.setUsers(userSet2);

        List<Role> roleList = new ArrayList<>();
        roleList.add(role1);
        roleList.add(role2);

        Mockito.doReturn(roleList)
                .when(roleRepository)
                .findAll();

        // WHEN
        List<Role> roles = roleService.allRoles();

        // THEN
        Assert.assertTrue(CoreMatchers.is(roles.size())
                .matches(2));
    }
}
