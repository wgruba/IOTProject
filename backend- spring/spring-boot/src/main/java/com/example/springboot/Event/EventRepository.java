package com.example.springboot.Event;

import com.example.springboot.Event.Exceptions.EventExistsEx;
import com.example.springboot.Event.Exceptions.EventNotFoundEx;
import com.example.springboot.User.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends MongoRepository<Event, String> {


    List<Event> findAll();
    Event findById(int id);
    @Query("{'$or': [{'name': ?0}]")
    List<Event> findByName(String name);
    Optional<Event> findTopByOrderByIdDesc();


    /*List<Event> getAllEvents();
    List<Event> getEventsByOrganiser(int organiserId);
    List<Event> getUsersSubscribedEvents(List<Integer> ids);
    List<Event> getEditedEvents();
    List<Event> getEventsFromCategory(int id);
    List<Event> getFilteredEvents(String name,
                                  int categoryId,
                                  int sizeMin,
                                  int sizeMax,
                                  String localisation,
                                  int isFree,
                                  int isReservationNecessary,
                                  String ageGroupMin,
                                  LocalDateTime startDate,
                                  LocalDateTime endDate,
                                  boolean isFullEventIncludedInDate);

    //CRUD
    Event getEvent(int id) throws EventNotFoundEx;
    Event getEvent(String name) throws EventNotFoundEx;
    boolean updateEvent(int id,
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
                        EventStatus eventStatus) throws EventNotFoundEx;
    boolean deleteEvent(int id) throws EventNotFoundEx;
    boolean addEvent(int id,
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
                     EventStatus eventStatus) throws EventExistsEx;
    boolean addEvent(Event event) throws EventExistsEx;


    //Subscription management
    boolean subscribeUser(int eventId, int userId) throws EventNotFoundEx;
    boolean subscribeCategory(int eventId, int categoryId) throws EventNotFoundEx;
    boolean unsubscribeUser(int eventId, int userId) throws EventNotFoundEx;
    boolean unsubscribeCategory(int eventId, int categoryId) throws EventNotFoundEx;


    //manage events' updates
    boolean changeEventStatus(int eventId, EventStatus eventStatus) throws EventNotFoundEx;
    boolean verifyEditedEvent(int eventId, int event2Id) throws EventNotFoundEx;
    boolean isEventActive(int id) throws EventNotFoundEx;

    boolean isFullEventIncludedInDate(int id, LocalDateTime startDate, LocalDateTime endDate) throws EventNotFoundEx;*/
}
