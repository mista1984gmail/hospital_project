package com.mista.soft.hospital_project.util;

import com.mista.soft.hospital_project.model.entity.HistorySick;
import com.mista.soft.hospital_project.model.entity.Role;
import com.mista.soft.hospital_project.model.entity.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserFixture {

    private static final String FIRST_NAME = "Ivan";
    private static final String LAST_NAME = "Ivanov";
    private static final LocalDate BIRTHDAY = LocalDate.of(2000, 1, 1);;
    private static final Integer AGE = 22;
    private static final String ADDRESS = "Grodno 123";
    private static final String EMAIL = "ivanov@mail.ru";
    private static final String TELEPHONE = " +375-29-555-55-55";
    private static final String USERNAME = "login";
    private static final String PASSWORD = "12345";
    private static final String PASSWORD_CONFIRM = "12345";
    private static final boolean ACTIVE = true;
    private static final String ACTIVATION_CODE = "12345";
    private static Set<Role> ROLES;
    private static List<HistorySick> HISTORY_SICKS = new ArrayList<>();

    public static User createUser(){
        User user = new User();
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setBirthday(BIRTHDAY);
        user.setAge(AGE);
        user.setAddress(ADDRESS);
        user.setEmail(EMAIL);
        user.setTelephone(TELEPHONE);
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setPasswordConfirm(PASSWORD_CONFIRM);
        user.setActive(ACTIVE);
        user.setActivationCode(ACTIVATION_CODE);
        user.setRoles(ROLES);
        user.setHistorySicks(HISTORY_SICKS);
        return user;
    }
}
