package com.example.springboot.Event;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import java.time.LocalDateTime;
import java.util.List;

public class Event {
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private int id;
    @BsonProperty
    private String name;
    @BsonProperty
    private int organizer;
    @BsonProperty
    private List<Integer> categoryList;
    @BsonProperty
    private List<Integer> clientList;
    @BsonProperty
    private String description;
    @BsonProperty
    private int size;

    //    todo localisation as localisation
    @BsonProperty
    private String localisation;
    @BsonProperty
    private boolean isFree; //not paid
    @BsonProperty
    private boolean isReservationNecessary;
    @BsonProperty
    @BsonRepresentation(BsonType.STRING)
    private AgeGroup ageGroup;
    @BsonProperty
    private LocalDateTime startDate;
    @BsonProperty
    private LocalDateTime endDate;
    @BsonProperty
    @BsonRepresentation(BsonType.STRING)
    private EventStatus eventStatus;

    public Event() {
    }

    public Event(@BsonId int id,
                 @BsonProperty String name,
                 @BsonProperty int organizer,
                 @BsonProperty List<Integer> categoryList,
                 @BsonProperty List<Integer> clientList,
                 @BsonProperty String description,
                 @BsonProperty int size,
                 @BsonProperty String localisation,
                 @BsonProperty boolean isFree,
                 @BsonProperty boolean isReservationNecessary,
                 @BsonProperty AgeGroup ageGroup,
                 @BsonProperty LocalDateTime startDate,
                 @BsonProperty LocalDateTime endDate,
                 @BsonProperty EventStatus eventStatus) {
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
        this.ageGroup = ageGroup;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventStatus = eventStatus;
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

    public int getOrganizer() {
        return organizer;
    }

    public void setOrganizer(int organizer) {
        this.organizer = organizer;
    }

    public List<Integer> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Integer> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Integer> getUserList() {
        return clientList;
    }

    public void setUserList(List<Integer> clientList) {
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

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }
}