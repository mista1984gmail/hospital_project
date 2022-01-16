package com.mista.soft.hospital_project.exceptions;

public class IdIsNotFoundOnDbException extends RuntimeException{
    public IdIsNotFoundOnDbException(int id) {
        super("The id = '" + id + "' is not found on DB");}
}

