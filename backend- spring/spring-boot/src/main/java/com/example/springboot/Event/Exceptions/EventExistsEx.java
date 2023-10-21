package com.example.springboot.Event.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class EventExistsEx extends Exception {
    public EventExistsEx(int id){
        super(" " + id + " ");
    }
}
