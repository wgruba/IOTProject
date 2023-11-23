package com.example.springboot.Category.Exceptions;

public class CategoryNotFoundEx extends Exception {

    public CategoryNotFoundEx(String name){
        super(" name: " + name + " ");
    }

}
