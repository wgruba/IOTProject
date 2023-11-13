package com.example.springboot.Event;

import com.example.springboot.Event.Exceptions.EventExistsEx;
import com.example.springboot.Event.Exceptions.EventNotFoundEx;

import java.time.LocalDateTime;
import java.util.List;

public class EventRepositoryImpl implements EventRepository{
    public EventRepositoryImpl(){}
    @Override
    public List<Event> getAllEvents() {
        System.out.println("getAllEvents");
        //todo link to Agata's function: Events Read bez_filtra
        return null;
    }

    @Override
    public List<Event> getEventsByOrganiser(int organiserId) {
        System.out.println("getEventsByOrganiser: " + organiserId);
        //todo link to Agata's function: Events Read filrt (idOrganizatora = id)
        return null;
    }

    @Override
    public List<Event> getUsersSubscribedEvents(List<Integer> ids) {
        System.out.println("getUsersSubscribedEvents: " + ids);
        //todo link to Agata's function: Events Read filtr in("id", ids)
        return null;
    }

    @Override
    public List<Event> getEditedEvents() {
        System.out.println("getEditedEvents");
        //todo link to Agata's function: Events Read filtr eq("status" = EventStatus.EDITED)
        return null;
    }

    @Override
    public List<Event> getEventsFromCategory(int id) {
        System.out.println("getEventsFromCategory: " + id);
        //todo link to Agata's function: Events Read filtr elemMatch("categoryList", Bson eq(id))
        return null;
    }

    @Override
    public List<Event> getFilteredEvents(String name,
                                         int categoryId,
                                         int sizeMin,
                                         int sizeMax,
                                         String localisation,
                                         int isFree,                // 0-false; 1-true; 2- both
                                         int isReservationNecessary,// 0-false; 1-true; 2- both
                                         String ageGroupMin,
                                         LocalDateTime startDate,
                                         LocalDateTime endDate,
                                         boolean isFullEventIncludedInDate){
        System.out.println("getFilteredEvents: " + name + " " + categoryId + " " + sizeMin + " " + sizeMax + " " + localisation + " " + isFree + " " + isReservationNecessary + " " + ageGroupMin + " " + startDate + " " + endDate + " " + isFullEventIncludedInDate);
        //todo podlinkować funkcję Agaty; Event R filtr:
        //and(
        // or(name.equals(""), eq("name", name)),
        // gte("size", sizeMin),
        // lte("size", sizeMax),
        // or(localisation.equals(""), eq("localisation", localisation)),
        // or(isFree == 2, and(isFree != 2, eq("isFree", isFree))),
        // or(isReservationNecessary == 2, and(isReservationNecessary != 2, eq("isReservationNecessary", isReservationNecessary))),

        //  gte("ageGroup", ageGroupMin),
        //  or(
        //    ageGroupMin.equals("FAMILY_FRIENDLY"),
        //    and(ageGroupMin.equals("OVER12"), ne("ageGroup", "FAMILY_FRIENDLY")),
        //    and(ageGroupMin.equals("OVER16"), and(ne("ageGroup", "FAMILY_FRIENDLY"), ne("ageGroup", "OVER12"))),
        //    and(ageGroupMin.equals("OVER18"), eq("ageGroup", "OVER18")))
        //  or(
        //    and(isFullEventIncludedInDate, gte("startDate", startDate), lte("startDate", endDate), gte("endDate", startDate), lte("endDate", endDate)),
        //    and(!isFullEventIncludedInDate, or(and(gte("startDate", startDate), lte("startDate", endDate)), and(gte("endDate", startDate), lte("endDate", endDate)), and(lte("startDate", startDate), gte("endDate", endDate))))))
        return null;
    }

    @Override
    public Event getEvent(int id) throws EventNotFoundEx {
        System.out.println("getEvent: " + id);
        //todo link to Agata's function: Event Read (id = id)
        return null;
    }

    @Override
    public Event getEvent(String name) throws EventNotFoundEx {
        System.out.println("getEvent: " + name);
        //todo link to Agata's function: Event Read (name = name)
        return null;
    }

    @Override
    public Event updateEvent(int id,
                             String name,
                             int organizer,
                             List<Integer> categoryList,
                             List<Integer> userList,
                             String description,
                             int size,
                             String localisation,
                             boolean isFree,
                             boolean isReservationNecessary,
                             AgeGroup ageGroup,
                             LocalDateTime startDate,
                             LocalDateTime endDate,
                             EventStatus eventStatus) throws EventNotFoundEx {
        System.out.println("updateEvent: " + id + " " + name + " " + categoryList + " " + userList + " " + description + " " + size + " " + localisation + " " + isFree + " " + isReservationNecessary + " " + ageGroup + " " + startDate + " " + endDate + " " + eventStatus);
        //todo link to Agata's function: Events Update (id = id)
        return null;
    }

    @Override
    public boolean deleteEvent(int id) throws EventNotFoundEx {
        System.out.println("deleteEvent: " + id);
        //todo link to Agata's function: Event Delete filtr eq("name" = name)
        return false;
    }

    @Override
    public boolean addEvent(int id,
                            String name,
                            int organizer,
                            List<Integer> categoryList,
                            List<Integer> userList,
                            String description,
                            int size,
                            String localisation,
                            boolean isFree,
                            boolean isReservationNecessary,
                            AgeGroup ageGroup,
                            LocalDateTime startDate,
                            LocalDateTime endDate,
                            EventStatus eventStatus) throws EventExistsEx {
        System.out.println("addEvent: " + id + " " + name + " " + categoryList + " " + userList + " " + description + " " + size + " " + localisation + " " + isFree + " " + isReservationNecessary + " " + ageGroup + " " + startDate + " " + endDate + " " + eventStatus);
        //todo link to Agata's function: Event Create
        return false;
    }

    @Override
    public boolean addEvent(Event event) throws EventExistsEx {
        System.out.println("addEvent: " + event);
        //todo link to Agata's function: Event Create
        return false;
    }

    @Override
    public boolean subscribeUser(int eventId, int userId) throws EventNotFoundEx {
        System.out.println("subscribeUser: " + " " + eventId + " " + userId);
        Event tempEvent = getEvent(eventId);
        List<Integer> tempList = tempEvent.getUserList();
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
                tempEvent.getAgeGroup(),
                tempEvent.getStartDate(),
                tempEvent.getEndDate(),
                tempEvent.getEventStatus());
        return true;
    }

    @Override
    public boolean subscribeCategory(int eventId, int categoryId) throws EventNotFoundEx {
        System.out.println("subscribeCategory: " + eventId + " " + categoryId);
        Event tempEvent = getEvent(eventId);
        List<Integer> tempList = tempEvent.getCategoryList();
        tempList.add(categoryId);

        updateEvent(eventId,
                tempEvent.getName(),
                tempEvent.getOrganizer(),
                tempList,
                tempEvent.getUserList(),
                tempEvent.getDescription(),
                tempEvent.getSize(),
                tempEvent.getLocalisation(),
                tempEvent.isFree(),
                tempEvent.isReservationNecessary(),
                tempEvent.getAgeGroup(),
                tempEvent.getStartDate(),
                tempEvent.getEndDate(),
                tempEvent.getEventStatus());
        return true;
    }

    @Override
    public boolean unsubscribeUser(int eventId, int userId) throws EventNotFoundEx {
        System.out.println("unsubscribeUser: " + eventId + " " + userId);
        Event tempEvent = getEvent(eventId);
        List<Integer> tempList = tempEvent.getUserList();
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
                tempEvent.getAgeGroup(),
                tempEvent.getStartDate(),
                tempEvent.getEndDate(),
                tempEvent.getEventStatus());
        return true;
    }

    @Override
    public boolean unsubscribeCategory(int eventId, int categoryId) throws EventNotFoundEx {
        System.out.println("unsubscribeCategory: " + " " + eventId + " " + categoryId);
        Event tempEvent = getEvent(eventId);
        List<Integer> tempList = tempEvent.getCategoryList();
        tempList.remove(categoryId);

        updateEvent(eventId,
                tempEvent.getName(),
                tempEvent.getOrganizer(),
                tempList,
                tempEvent.getUserList(),
                tempEvent.getDescription(),
                tempEvent.getSize(),
                tempEvent.getLocalisation(),
                tempEvent.isFree(),
                tempEvent.isReservationNecessary(),
                tempEvent.getAgeGroup(),
                tempEvent.getStartDate(),
                tempEvent.getEndDate(),
                tempEvent.getEventStatus());
        return false;
    }

    @Override
    public boolean changeEventStatus(int eventId, EventStatus eventStatus) throws EventNotFoundEx {
        System.out.println("changeEventStatus: " + eventId + " " + eventStatus);
        Event tempEvent = getEvent(eventId);

        updateEvent(eventId,
                tempEvent.getName(),
                tempEvent.getOrganizer(),
                tempEvent.getCategoryList(),
                tempEvent.getUserList(),
                tempEvent.getDescription(),
                tempEvent.getSize(),
                tempEvent.getLocalisation(),
                tempEvent.isFree(),
                tempEvent.isReservationNecessary(),
                tempEvent.getAgeGroup(),
                tempEvent.getStartDate(),
                tempEvent.getEndDate(),
                eventStatus);
        return true;
    }

    @Override
    public boolean verifyEditedEvent(int eventId, int event2Id) throws EventNotFoundEx {
        System.out.println("verifyEditedEvent: " + eventId + " " + event2Id);
        Event tempEventEdited = getEvent(event2Id);

        updateEvent(eventId,
                tempEventEdited.getName(),
                tempEventEdited.getOrganizer(),
                tempEventEdited.getCategoryList(),
                tempEventEdited.getUserList(),
                tempEventEdited.getDescription(),
                tempEventEdited.getSize(),
                tempEventEdited.getLocalisation(),
                tempEventEdited.isFree(),
                tempEventEdited.isReservationNecessary(),
                tempEventEdited.getAgeGroup(),
                tempEventEdited.getStartDate(),
                tempEventEdited.getEndDate(),
                EventStatus.ACCEPTED);
        return false;
    }

    @Override
    public boolean isEventActive(int id) throws EventNotFoundEx {
        System.out.println("isEventActive: " + id);
        Event tempEvent = getEvent(id);
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(tempEvent.getStartDate()) && now.isBefore(tempEvent.getEndDate());
    }

    public boolean isFullEventIncludedInDate(int id, LocalDateTime startDate, LocalDateTime endDate) throws EventNotFoundEx {
        System.out.println("isFullEventIncludedInDate: " + id + " " + startDate + " " + endDate);
        Event tempEvent = getEvent(id);
        return startDate.isAfter(tempEvent.getStartDate()) && endDate.isBefore(tempEvent.getEndDate());
    }
}
