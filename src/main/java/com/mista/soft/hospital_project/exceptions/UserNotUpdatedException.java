package com.mista.soft.hospital_project.exceptions;

import com.mista.soft.hospital_project.model.entity.User;

public class UserNotUpdatedException extends RuntimeException{
    public UserNotUpdatedException(User user) {
        super("The id = '" + user.getId() + "' is not updated on DB");}
}
