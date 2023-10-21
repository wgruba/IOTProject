package com.example.springboot.Event;

import java.time.LocalDateTime;
import java.util.List;

public class Event {
    private int id;
    private String name;
    private int organizer;
    private List<Integer> categoryList;
    private List<Integer> clientList;
    private String description;
    private int size;

//    todo localisation as localisation
    private String localisation;
    private boolean isFree;
    private boolean isReservationNecessary;
    private boolean isLive; //not online
    private AgeGroup ageGroup;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Event() {
    }

    public Event(int id, String name, int organizer, List<Integer> categoryList, List<Integer> clientList, String description, int size, String localisation, boolean isFree, boolean isReservationNecessary, boolean isLive, AgeGroup ageGroup, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.name = name;
        this.organizer = organizer;
        this.categoryList = categoryList;
        this.clientList = clientList;
        this.description = description;
        this.size = size;
        this.localisation = localisation;
        this.isFree = isFree;
        this.isReservationNecessary = isReservationNecessary;
        this.isLive = isLive;
        this.ageGroup = ageGroup;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getorganizer() {
        return organizer;
    }

    public void setorganizer(int organizer) {
        this.organizer = organizer;
    }

    public List<Integer> getcategoryList() {
        return categoryList;
    }

    public void setcategoryList(List<Integer> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Integer> getClientList() {
        return clientList;
    }

    public void setClientList(List<Integer> clientList) {
        this.clientList = clientList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public boolean isReservationNecessary() {
        return isReservationNecessary;
    }

    public void setReservationNecessary(boolean reservationNecessary) {
        isReservationNecessary = reservationNecessary;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
