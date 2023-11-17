package com.example.springboot.User.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundEx extends Exception {
    public UserNotFoundEx(int index){
        super(" id: " + index + " ");
    }
    public UserNotFoundEx(String name){
            super(" name: " + name + " ");
        }
}
