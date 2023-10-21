package com.example.springboot.User.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UserExistsEx extends Exception {
    public UserExistsEx(int id){
        super(" " + id + " ");
    }
}
