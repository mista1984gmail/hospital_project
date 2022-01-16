package com.mista.soft.hospital_project.exceptions;

import com.mista.soft.hospital_project.model.entity.User;

public class UserNotSavedException extends RuntimeException{
    public UserNotSavedException(User user) {
        super("The id = '" + user.getId() + "' is not saved on DB");}
}
