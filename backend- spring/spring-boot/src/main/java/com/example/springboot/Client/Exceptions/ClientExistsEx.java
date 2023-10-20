package com.example.springboot.Client.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ClientExistsEx extends Exception {
    public ClientExistsEx(int id){
        super(" " + id + " ");
    }
}
