package com.example.springboot.Event;

import com.example.springboot.Event.Exceptions.EventExistsEx;
import com.example.springboot.Event.Exceptions.EventNotFoundEx;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository {
    List<Event> getAllEvents();
    List<Event> getEventsByOrganiser(int organiserId);
    List<Event> getUsersSubscribedEvents(List<Integer> ids);
    List<Event> getEditedEvents();
    List<Event> getEventsFromCategory(int id);

    //CRUD
    Event getEvent(int id) throws EventNotFoundEx;
    Event getEvent(String name) throws EventNotFoundEx;
    Event updateEvent(int id, String name, int organizer, List<Integer> categoryList, List<Integer> clientList, String description, int size, String localisation, boolean isFree, boolean isReservationNecessary, boolean isLive, AgeGroup ageGroup, LocalDateTime startDate, LocalDateTime endDate, EventStatus eventStatus) throws EventNotFoundEx;
    boolean deleteEvent(int id) throws EventNotFoundEx;
    boolean addEvent(int id, String name, int organizer, List<Integer> categoryList, List<Integer> clientList, String description, int size, String localisation, boolean isFree, boolean isReservationNecessary, boolean isLive, AgeGroup ageGroup, LocalDateTime startDate, LocalDateTime endDate, EventStatus eventStatus) throws EventExistsEx;
    boolean addEvent(Event event) throws EventExistsEx;


    //Subscription management
    boolean subscribeUser(int eventId, int userId) throws EventNotFoundEx;
    boolean subscribeCategory(int eventId, int categoryId) throws EventNotFoundEx;
    boolean unsubscribeUser(int eventId, int userId) throws EventNotFoundEx;
    boolean unsubscribeCategory(int eventId, int categoryId) throws EventNotFoundEx;


    //manage events' updates
    boolean changeEventStatus(int eventId, EventStatus eventStatus) throws EventNotFoundEx;
    boolean verifyEditedEvent(int eventId, int event2Id) throws EventNotFoundEx;

    List<Event> getFilteredEvents(String name,
                                  int categoryId,
                                  int sizeMin,
                                  int sizeMax,
                                  String localisation,
                                  int optionalBooleanValues,
                                  boolean isFree,
                                  boolean isReservationNecessary,
                                  boolean isLive,
                                  AgeGroup ageGroupMin,
                                  AgeGroup ageGroupMax,
                                  LocalDateTime startDate,
                                  LocalDateTime endDate);


}
