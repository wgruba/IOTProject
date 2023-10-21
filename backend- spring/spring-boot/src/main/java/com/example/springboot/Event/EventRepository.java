package com.example.springboot.Event;

import com.example.springboot.User.User;
import com.example.springboot.Event.Exceptions.EventExistsEx;
import com.example.springboot.Event.Exceptions.EventNotFoundEx;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository {
    List<Event> getAllEvents();
    Event getEvent(int id) throws EventNotFoundEx;
    Event getEvent(String nameOrMail) throws EventNotFoundEx;

    Event updateEvent(int id, String name, int organizer, List<Integer> categoryList, List<Integer> clientList, String description, int size, String localisation, boolean isFree, boolean isReservationNecessary, boolean isLive, AgeGroup ageGroup, LocalDateTime startDate, LocalDateTime endDate) throws EventNotFoundEx;
    boolean deleteEvent(int id) throws EventNotFoundEx;
    Event addEvent(int id, String name, int organizer, List<Integer> categoryList, List<Integer> clientList, String description, int size, String localisation, boolean isFree, boolean isReservationNecessary, boolean isLive, AgeGroup ageGroup, LocalDateTime startDate, LocalDateTime endDate) throws EventExistsEx;
    Event addEvent(Event event) throws EventExistsEx;

    boolean addClientsToEvent(int id, User client);

    List<Integer> getEventsClientList(int id);
    List<Integer> getEventsCategoryList(int id);
    int countEventsClientList(int id);

}
