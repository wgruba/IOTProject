package com.example.springboot.User;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.*;

import java.util.List;

public class User {
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private int id;
    @BsonProperty
    private String name;
    @BsonProperty
    private String mail;
    @BsonProperty
    private String password;
    @BsonProperty
    @BsonRepresentation(BsonType.STRING)
    private PermissionLevel permissionLevel;
    @BsonProperty
    private List<Integer> subscribedEvents;
    @BsonProperty
    private List<Integer> subscribedCategories;

    public User() {

    }

    @BsonCreator
    public User(@BsonId int id,
                @BsonProperty String name,
                @BsonProperty String mail,
                @BsonProperty String password,
                @BsonProperty PermissionLevel permissionLevel,
                @BsonProperty List<Integer> subscribedEvents,
                @BsonProperty List<Integer> subscribedCategories) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.permissionLevel = permissionLevel;
        this.subscribedEvents = subscribedEvents;
        this.subscribedCategories = subscribedCategories;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(PermissionLevel permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public List<Integer> getSubscribedEvents() {
        return subscribedEvents;
    }

    public void setSubscribedEvents(List<Integer> subscribedEvents) {
        this.subscribedEvents = subscribedEvents;
    }

    public List<Integer> getSubscribedCategories() {
        return subscribedCategories;
    }

    public void setSubscribedCategories(List<Integer> subscribedCategories) {
        this.subscribedCategories = subscribedCategories;
    }
}
