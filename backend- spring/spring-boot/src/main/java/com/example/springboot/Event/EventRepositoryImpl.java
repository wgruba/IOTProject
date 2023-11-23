/*
package com.example.springboot.Event;

import com.example.springboot.Event.Exceptions.EventExistsEx;
import com.example.springboot.Event.Exceptions.EventNotFoundEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import java.time.LocalDateTime;
import java.util.List;

public class EventRepositoryImpl  implements EventRepository{
    @Autowired
    MongoTemplate mongoTemplate;
    private final String USERNAME = "Agata";
    private final String PASSWORD = "haslo";
    private final String DATABASE = "bazadanych";

    public EventRepositoryImpl() {
    }

    @Override
    public List<Event> getAllEvents() {
        return null;
    }

    @Override
    public List<Event> getEventsByOrganiser(int organiserId) {
        return null;
    }

    @Override
    public List<Event> getUsersSubscribedEvents(List<Integer> ids) {
        return null;
    }

    @Override
    public List<Event> getEditedEvents() {
        return null;
    }

    @Override
    public List<Event> getEventsFromCategory(int id) {
        return null;
    }

    @Override
    public List<Event> getFilteredEvents(String name, int categoryId, int sizeMin, int sizeMax, String localisation, int isFree, int isReservationNecessary, String ageGroupMin, LocalDateTime startDate, LocalDateTime endDate, boolean isFullEventIncludedInDate) {
        return null;
    }

    @Override
    public Event getEvent(int id) throws EventNotFoundEx {
        return null;
    }

    @Override
    public Event getEvent(String name) throws EventNotFoundEx {
        return null;
    }

    @Override
    public boolean updateEvent(int id, String name, int organizer, List<Integer> categoryList, List<Integer> userList, String description, int size, String localisation, boolean isFree, boolean isReservationNecessary, AgeGroup ageGroup, LocalDateTime startDate, LocalDateTime endDate, EventStatus eventStatus) throws EventNotFoundEx {
        return false;
    }

    @Override
    public boolean deleteEvent(int id) throws EventNotFoundEx {
        return false;
    }

    @Override
    public boolean addEvent(int id, String name, int organizer, List<Integer> categoryList, List<Integer> userList, String description, int size, String localisation, boolean isFree, boolean isReservationNecessary, AgeGroup ageGroup, LocalDateTime startDate, LocalDateTime endDate, EventStatus eventStatus) throws EventExistsEx {
        return false;
    }

    @Override
    public boolean addEvent(Event event) throws EventExistsEx {
        return false;
    }

    @Override
    public boolean subscribeUser(int eventId, int userId) throws EventNotFoundEx {
        return false;
    }

    @Override
    public boolean subscribeCategory(int eventId, int categoryId) throws EventNotFoundEx {
        return false;
    }

    @Override
    public boolean unsubscribeUser(int eventId, int userId) throws EventNotFoundEx {
        return false;
    }

    @Override
    public boolean unsubscribeCategory(int eventId, int categoryId) throws EventNotFoundEx {
        return false;
    }

    @Override
    public boolean changeEventStatus(int eventId, EventStatus eventStatus) throws EventNotFoundEx {
        return false;
    }

    @Override
    public boolean verifyEditedEvent(int eventId, int event2Id) throws EventNotFoundEx {
        return false;
    }

    @Override
    public boolean isEventActive(int id) throws EventNotFoundEx {
        return false;
    }

    @Override
    public boolean isFullEventIncludedInDate(int id, LocalDateTime startDate, LocalDateTime endDate) throws EventNotFoundEx {
        return false;
    }

    */
/*@Override
    public List<Event> getAllEvents() {
        System.out.println("getAllEvents");

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        return dc.EventRead(df.allEntries());
    }

    @Override
    public List<Event> getEventsByOrganiser(int organiserId) {
        System.out.println("getEventsByOrganiser: " + organiserId);

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        return dc.EventRead(df.findThroughOrganizerId(organiserId));
    }

    @Override
    public List<Event> getUsersSubscribedEvents(List<Integer> ids) {
        System.out.println("getUsersSubscribedEvents: " + ids);

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        return dc.EventRead(df.findAllIds(ids));
    }

    @Override
    public List<Event> getEditedEvents() {
        System.out.println("getEditedEvents");

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        return dc.EventRead(df.findSpecificStatus(EventStatus.EDITED));
    }

    @Override
    public List<Event> getEventsFromCategory(int id) {
        System.out.println("getEventsFromCategory: " + id);

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        return dc.EventRead(df.findAllInCategory(id));
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
                                         boolean isFullEventIncludedInDate) {
        System.out.println("getFilteredEvents: " + name + " " + categoryId + " " + sizeMin + " " + sizeMax + " " + localisation + " " + isFree + " " + isReservationNecessary + " " + ageGroupMin + " " + startDate + " " + endDate + " " + isFullEventIncludedInDate);

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();

        //todo podlinkować funkcję Agaty

        return null;
    }

    @Override
    public Event getEvent(int id) throws EventNotFoundEx {
        System.out.println("getEvent: " + id);

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        return dc.EventRead(df.findById(id)).get(0);
    }

    @Override
    public Event getEvent(String name) throws EventNotFoundEx {
        System.out.println("getEvent: " + name);
        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        return dc.EventRead(df.findByName(name)).get(0);
    }

    @Override
    public boolean updateEvent(int id,
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

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        DatabaseUpdater du = new DatabaseUpdater();

        Bson update_fields = du.combineUpdates(
                du.updateName(name),
                du.updateDescription(description),
                du.updateCategoryList(categoryList),
                du.updateUserSubscription(userList),
                du.updateSize(size),
                du.updateLocalisation(localisation),
                du.updateIsFree(isFree),
                du.updateIsReservationNecessary(isReservationNecessary),
                du.updateAgeGroup(ageGroup),
                du.updateStartDate(startDate),
                du.updateEndDate(endDate),
                du.updateEventStatus(eventStatus)
        );
        return dc.EventCUD(new Bson[]{df.findById(id)}, new Bson[]{update_fields});
    }

    @Override
    public boolean deleteEvent(int id) throws EventNotFoundEx {
        System.out.println("deleteEvent: " + id);

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        return dc.EventCUD(new Bson[]{df.findById(id)});
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

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);

        Event event = new Event(id, name, organizer, categoryList, userList, description, size, localisation,
                isFree, isReservationNecessary, ageGroup, startDate, endDate, eventStatus);
        return dc.EventCUD(new Event[]{event});
    }

    @Override
    public boolean addEvent(Event event) throws EventExistsEx {
        System.out.println("addEvent: " + event);

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        return dc.EventCUD(new Event[]{event});
    }

    @Override
    public boolean subscribeUser(int eventId, int userId) throws EventNotFoundEx {
        System.out.println("subscribeUser: " + " " + eventId + " " + userId);

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        DatabaseUpdater du = new DatabaseUpdater();

        return dc.EventCUD(new Bson[]{df.findById(eventId)}, new Bson[]{du.addUserSubscription(userId)});
    }

    @Override
    public boolean subscribeCategory(int eventId, int categoryId) throws EventNotFoundEx {
        System.out.println("subscribeCategory: " + eventId + " " + categoryId);

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        DatabaseUpdater du = new DatabaseUpdater();

        return dc.EventCUD(new Bson[]{df.findById(eventId)}, new Bson[]{du.addToCategory(categoryId)});
    }

    @Override
    public boolean unsubscribeUser(int eventId, int userId) throws EventNotFoundEx {
        System.out.println("unsubscribeUser: " + eventId + " " + userId);
        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        DatabaseUpdater du = new DatabaseUpdater();

        return dc.EventCUD(new Bson[]{df.findById(eventId)}, new Bson[]{du.removeUserSubscription(userId)});
    }

    @Override
    public boolean unsubscribeCategory(int eventId, int categoryId) throws EventNotFoundEx {
        System.out.println("unsubscribeCategory: " + " " + eventId + " " + categoryId);
        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        DatabaseUpdater du = new DatabaseUpdater();

        return dc.EventCUD(new Bson[]{df.findById(eventId)}, new Bson[]{du.removeFromCategory(categoryId)});
    }

    @Override
    public boolean changeEventStatus(int eventId, EventStatus eventStatus) throws EventNotFoundEx {
        System.out.println("changeEventStatus: " + eventId + " " + eventStatus);
        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        DatabaseUpdater du = new DatabaseUpdater();

        return dc.EventCUD(new Bson[]{df.findById(eventId)}, new Bson[]{du.updateEventStatus(eventStatus)});
    }

    @Override
    public boolean verifyEditedEvent(int eventId, int event2Id) throws EventNotFoundEx {
        System.out.println("verifyEditedEvent: " + eventId + " " + event2Id);
        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        DatabaseUpdater du = new DatabaseUpdater();

        // klaryfikacja - jak ma dokladnie dzialac

        return dc.EventCUD(new Bson[]{df.findById(eventId)}, new Bson[]{du.updateEventStatus(EventStatus.ACCEPTED)}) &&
                dc.EventCUD(new Bson[]{df.findById(event2Id)});
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
    }*//*

}
*/
