package com.example.springboot.Client.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ClientNotFoundEx extends Exception {
    public ClientNotFoundEx(int index){
        super(" id: " + index + " ");
    }
    public ClientNotFoundEx(String name){
            super(" name: " + name + " ");
        }
}
