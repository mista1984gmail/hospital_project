package com.mista.soft.hospital_project.exceptions;

public class UserNameNotFoundException extends RuntimeException {
    public UserNameNotFoundException() {
        super("User not found");}
}
