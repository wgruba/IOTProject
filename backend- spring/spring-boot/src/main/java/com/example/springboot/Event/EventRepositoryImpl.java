package com.example.springboot.Event;

import com.example.springboot.Event.Exceptions.EventExistsEx;
import com.example.springboot.Event.Exceptions.EventNotFoundEx;

import java.time.LocalDateTime;
import java.util.List;

public class EventRepositoryImpl implements EventRepository{
    public EventRepositoryImpl(){}
    @Override
    public List<Event> getAllEvents() {
        //todo podlinkować do funkcji Agaty: Events Read bez_filtra
        return null;
    }

    @Override
    public List<Event> getEventsByOrganiser(int organiserId) {
        //todo podlinkować do funkcji Agaty: Events Read filrt (idOrganizatora = id)
        return null;
    }

    @Override
    public List<Event> getUsersSubscribedEvents(List<Integer> ids) {
        //todo podlinkować do funkcji Agaty: Events Read filtr ?
        return null;
    }

    @Override
    public List<Event> getEditedEvents() {
        //todo podlinkować do funkcji Agaty: Events Read filtr (status = EDITED)
        return null;
    }

    @Override
    public List<Event> getEventsFromCategory(int id) {
        //todo podlinkować do funkcji Agaty: Events Read filtr ?
        return null;
    }

    @Override
    public Event getEvent(int id) throws EventNotFoundEx {
        //todo podlinkować do funkcji Agaty: Event Read (id = id)
        return null;
    }

    @Override
    public Event getEvent(String name) throws EventNotFoundEx {
        //todo podlinkować do funkcji Agaty: Event Read (name = name)
        return null;
    }

    @Override
    public Event updateEvent(int id,
                             String name,
                             int organizer,
                             List<Integer> categoryList,
                             List<Integer> clientList,
                             String description,
                             int size,
                             String localisation,
                             boolean isFree,
                             boolean isReservationNecessary,
                             boolean isLive,
                             AgeGroup ageGroup,
                             LocalDateTime startDate,
                             LocalDateTime endDate,
                             EventStatus eventStatus) throws EventNotFoundEx {
        //todo podlinkować do funkcji Agaty: Events Update (id = id)
        return null;
    }

    @Override
    public boolean deleteEvent(int id) throws EventNotFoundEx {
        //todo podlinkować do funkcji Agaty: Event Delete filtr (name = name)
        return false;
    }

    @Override
    public Event addEvent(int id,
                          String name,
                          int organizer,
                          List<Integer> categoryList,
                          List<Integer> clientList,
                          String description,
                          int size,
                          String localisation,
                          boolean isFree,
                          boolean isReservationNecessary,
                          boolean isLive,
                          AgeGroup ageGroup,
                          LocalDateTime startDate,
                          LocalDateTime endDate,
                          EventStatus eventStatus) throws EventExistsEx {
        //todo podlinkować do funkcji Agaty: Event Create
        return null;
    }

    @Override
    public Event addEvent(Event event) throws EventExistsEx {
        //todo podlinkować do funkcji Agaty: Event Create
        return null;
    }

    @Override
    public boolean subscribeUser(int eventId, int userId) throws EventNotFoundEx {
        Event tempEvent = getEvent(eventId);
        List<Integer> tempList = tempEvent.getClientList();
        tempList.add(userId);

        updateEvent(eventId,
                tempEvent.getName(),
                tempEvent.getOrganizer(),
                tempEvent.getCategoryList(),
                tempList,
                tempEvent.getDescription(),
                tempEvent.getSize(),
                tempEvent.getLocalisation(),
                tempEvent.isFree(),
                tempEvent.isReservationNecessary(),
                tempEvent.isLive(),
                tempEvent.getAgeGroup(),
                tempEvent.getStartDate(),
                tempEvent.getEndDate(),
                tempEvent.getEventStatus());
        return true;
    }

    @Override
    public boolean subscribeCategory(int eventId, int categoryId) throws EventNotFoundEx {
        Event tempEvent = getEvent(eventId);
        List<Integer> tempList = tempEvent.getCategoryList();
        tempList.add(categoryId);

        updateEvent(eventId,
                tempEvent.getName(),
                tempEvent.getOrganizer(),
                tempList,
                tempEvent.getClientList(),
                tempEvent.getDescription(),
                tempEvent.getSize(),
                tempEvent.getLocalisation(),
                tempEvent.isFree(),
                tempEvent.isReservationNecessary(),
                tempEvent.isLive(),
                tempEvent.getAgeGroup(),
                tempEvent.getStartDate(),
                tempEvent.getEndDate(),
                tempEvent.getEventStatus());
        return true;
    }

    @Override
    public boolean unsubscribeUser(int eventId, int userId) throws EventNotFoundEx {
        Event tempEvent = getEvent(eventId);
        List<Integer> tempList = tempEvent.getClientList();
        tempList.remove(userId);

        updateEvent(eventId,
                tempEvent.getName(),
                tempEvent.getOrganizer(),
                tempEvent.getCategoryList(),
                tempList,
                tempEvent.getDescription(),
                tempEvent.getSize(),
                tempEvent.getLocalisation(),
                tempEvent.isFree(),
                tempEvent.isReservationNecessary(),
                tempEvent.isLive(),
                tempEvent.getAgeGroup(),
                tempEvent.getStartDate(),
                tempEvent.getEndDate(),
                tempEvent.getEventStatus());
        return true;
    }

    @Override
    public boolean unsubscribeCategory(int eventId, int categoryId) throws EventNotFoundEx {
        Event tempEvent = getEvent(eventId);
        List<Integer> tempList = tempEvent.getCategoryList();
        tempList.remove(categoryId);

        updateEvent(eventId,
                tempEvent.getName(),
                tempEvent.getOrganizer(),
                tempList,
                tempEvent.getClientList(),
                tempEvent.getDescription(),
                tempEvent.getSize(),
                tempEvent.getLocalisation(),
                tempEvent.isFree(),
                tempEvent.isReservationNecessary(),
                tempEvent.isLive(),
                tempEvent.getAgeGroup(),
                tempEvent.getStartDate(),
                tempEvent.getEndDate(),
                tempEvent.getEventStatus());
        return false;
    }

    @Override
    public boolean changeEventStatus(int eventId, EventStatus eventStatus) throws EventNotFoundEx {
        Event tempEvent = getEvent(eventId);

        updateEvent(eventId,
                tempEvent.getName(),
                tempEvent.getOrganizer(),
                tempEvent.getCategoryList(),
                tempEvent.getClientList(),
                tempEvent.getDescription(),
                tempEvent.getSize(),
                tempEvent.getLocalisation(),
                tempEvent.isFree(),
                tempEvent.isReservationNecessary(),
                tempEvent.isLive(),
                tempEvent.getAgeGroup(),
                tempEvent.getStartDate(),
                tempEvent.getEndDate(),
                eventStatus);
        return true;
    }

    @Override
    public boolean verifyEditedEvent(int eventId, int event2Id) throws EventNotFoundEx {
        Event tempEventEdited = getEvent(event2Id);

        updateEvent(eventId,
                tempEventEdited.getName(),
                tempEventEdited.getOrganizer(),
                tempEventEdited.getCategoryList(),
                tempEventEdited.getClientList(),
                tempEventEdited.getDescription(),
                tempEventEdited.getSize(),
                tempEventEdited.getLocalisation(),
                tempEventEdited.isFree(),
                tempEventEdited.isReservationNecessary(),
                tempEventEdited.isLive(),
                tempEventEdited.getAgeGroup(),
                tempEventEdited.getStartDate(),
                tempEventEdited.getEndDate(),
                EventStatus.ACCEPTED);
        return false;
    }
}
