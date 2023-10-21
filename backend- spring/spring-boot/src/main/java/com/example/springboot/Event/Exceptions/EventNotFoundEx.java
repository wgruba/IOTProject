package com.example.springboot.Event.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EventNotFoundEx extends Exception {
    public EventNotFoundEx(int index){
        super(" id: " + index + " ");
    }


}
