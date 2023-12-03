package com.example.springboot.Event;

import com.example.springboot.Event.Exceptions.EventNotFoundEx;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends MongoRepository<Event, String> {

    //CRUD - Create & Update
    Event save(Event event);


    //CRUD - Read
    List<Event> findAll();
    Event findById(int id);
    Optional<Event> findTopByOrderByIdDesc();
    List<Event> findTop10ByOrderByIdDesc();
    @Query("{'id': ?0}")
    Optional<Event> getEventById(int eventId);
    @Query("{'name': ?0}")
    Optional<Event> getEventByName(String name);
    @Query("{'id': {'$in': ?0}}")
    List<Event> getEventsFromList(@Param("ids") List<Integer> ids);
    @Query("{'organizer': ?0}")
    List<Event> getEventsOrganisedByUser(@Param("id") int userId);
//    @Query("{?0': {'$in': 'categoryList'}")
    @Query("{'categoryList': {'$elemMatch': {'$eq': ?0}}}")
    List<Event> getEventsFromCategory(int id);
    @Query("{'eventStatus': TO_ACCEPTANCE}")
    List<Event> getEventsToAcceptance();


    // CRUD - Delete
    void deleteById(int id);


    // Account Management
    @Query("{'$or': [{'name': ?0}, {'mail': ?0}]}")
    Optional<Event> login(String nameOrMail, String password) throws EventNotFoundEx;
}
