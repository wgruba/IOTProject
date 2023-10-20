package com.example.springboot.Event;

import java.time.LocalDateTime;
import java.util.List;

public class Event {
    private int id;
    private String name;
    private int organisator;
    private List<Integer> categoriyList;
    private String description;
    private int size;
//    todo localisation
    private boolean isFree;
    private boolean isReservationNecessary;
    private boolean isLive; //not online
    //todo: make age limit a enumeration
    private int ageLimit;
    private LocalDateTime startDate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
