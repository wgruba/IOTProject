package com.example.springboot.Event;

import com.example.springboot.Category.Exceptions.CategoryNotFoundEx;
import com.example.springboot.User.Exceptions.NotEnoughHighPermissionLevel;
import com.example.springboot.User.Exceptions.UserNotFoundEx;
import com.example.springboot.User.PermissionLevel;
import com.example.springboot.User.User;
import com.example.springboot.Event.Exceptions.EventExistsEx;
import com.example.springboot.Event.Exceptions.EventNotFoundEx;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository {
    List<Event> getAllEvents();
    List<Event> getEventsByOrganiser(int organiserId);
    List<Event> getUsersSubscribedEvents(List<Integer> ids);
    List<Event> getEditedEvents();

    //CRUD
    Event getEvent(int id) throws EventNotFoundEx;
    Event getEvent(String nameOrMail) throws EventNotFoundEx;
    Event updateEvent(int id, String name, int organizer, List<Integer> categoryList, List<Integer> clientList, String description, int size, String localisation, boolean isFree, boolean isReservationNecessary, boolean isLive, AgeGroup ageGroup, LocalDateTime startDate, LocalDateTime endDate) throws EventNotFoundEx;
    boolean deleteEvent(int id) throws EventNotFoundEx;
    Event addEvent(int id, String name, int organizer, List<Integer> categoryList, List<Integer> clientList, String description, int size, String localisation, boolean isFree, boolean isReservationNecessary, boolean isLive, AgeGroup ageGroup, LocalDateTime startDate, LocalDateTime endDate) throws EventExistsEx;
    Event addEvent(Event event) throws EventExistsEx;


    //Subscription management
    boolean subscribeUser(int eventId, int userId) throws EventNotFoundEx, UserNotFoundEx;
    boolean subscribeCategory(int eventId, int categoryId) throws EventNotFoundEx, CategoryNotFoundEx;
    boolean unsubscribeUser(int eventId, int userId) throws EventNotFoundEx, UserNotFoundEx;
    boolean unsubscribeCategory(int eventId, int categoryId) throws EventNotFoundEx, CategoryNotFoundEx;


    //manage events' updates
    boolean changeEventStatus(int adminId, int eventId, EventStatus eventStatus) throws UserNotFoundEx, NotEnoughHighPermissionLevel, EventNotFoundEx;
    boolean verifyEditedEvent(int adminId, int eventId, int event2Id) throws UserNotFoundEx, NotEnoughHighPermissionLevel, EventNotFoundEx;

}
