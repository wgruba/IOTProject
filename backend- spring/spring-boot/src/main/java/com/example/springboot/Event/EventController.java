package com.example.springboot.Event;

import com.example.springboot.Category.Category;
import com.example.springboot.Category.CategoryController;
import com.example.springboot.User.User;
import com.example.springboot.User.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EventController {

    @Autowired
    private static EventRepository eventRepository;

    // CRUD - Create
    @PostMapping("/addEvent")
    public ResponseEntity<Event> addEvent(@RequestBody Event event) {
        Event savedEvent = eventRepository.save(event);
        return ResponseEntity.ok(savedEvent);
    }


    // CRUD - Read
    @GetMapping("/events")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return ResponseEntity.ok(events);
    }
    @GetMapping("/events/name/{name}")
    public ResponseEntity<List<Event>> getEventByName(@PathVariable String name) {
        List<Event> events = (List<Event>) eventRepository.getEventByName(name).get();
        return ResponseEntity.ok(events);
    }
    @GetMapping("/events/list")
    public static List<Event>getEventsFromList(List<Integer> ids){
        return eventRepository.getEventsFromList(ids);
    }
    @GetMapping("/events/eventsOrganisedBy/{userId}")
    public List<Event> getEventsByOrganiser(@PathVariable int userId) {
        return eventRepository.getEventsOrganisedByUser(userId);
    }
    @GetMapping("/events/category/{categoryId}")
    public List<Event> getEventsFromCategory(@PathVariable int categoryId) {
        return eventRepository.getEventsFromCategory(categoryId);
    }

    // CRUD - Delete
    @DeleteMapping("/events/{eventId}")
    public boolean deleteEvent(@PathVariable int eventId){
        eventRepository.deleteById(eventId);
        return true;
    }


    @GetMapping("/events/{eventId}/organiser")
    public int getEventOrganiser(@PathVariable int eventId){
        return eventRepository.findById(eventId).getOrganizer();
    }
    public List<Integer> getUsersThatSubscribedToEvent(int eventId){
        return eventRepository.findById(eventId).getUserList();
    }


    // Subscription Management
    @GetMapping("/events/{eventId}/subscribedUsers")
    public ResponseEntity<List<User>> getSubscribedEvents(@PathVariable int eventId) {
        Event tempEvent = eventRepository.findById(eventId);
        return ResponseEntity.ok(UserController.getUsersFromList((ArrayList<Integer>) tempEvent.getUserList()));
    }
    @GetMapping("/events/{eventId}/subscribedCategories")
    public ResponseEntity<List<Category>> getSubscribedCategories(@PathVariable int eventId) {
        Event tempEvent = eventRepository.findById(eventId);
        return ResponseEntity.ok(CategoryController.getCategoriesFromList(tempEvent.getCategoryList()));
    }
    public ResponseEntity<Boolean> subscribeUser(@PathVariable int userId, @PathVariable int eventId) {
        // jest wywoływane przez UserController.subscribeEvent
        Event tempEvent = eventRepository.findById(eventId);
        List<Integer> tempList = tempEvent.getClientList();
        tempList.add(userId);
        tempEvent.setUserList(tempList);
        eventRepository.save(tempEvent);
        return ResponseEntity.ok(true);
    }
    public ResponseEntity<Boolean> subscribeCategory(@PathVariable int eventId, @PathVariable int categoryId) {
        Event tempEvent = eventRepository.findById(eventId);
        List<Integer> tempList = tempEvent.getCategoryList();
        tempList.add(categoryId);
        tempEvent.setCategoryList(tempList);
        eventRepository.save(tempEvent);
        return ResponseEntity.ok(true);
    }
    public ResponseEntity<Boolean> unsubscribeUser(@PathVariable int userId, @PathVariable int eventId) {
        // jest wywoływane przez UserController.subscribeEvent
        Event tempEvent = eventRepository.findById(eventId);
        List<Integer> tempList = tempEvent.getClientList();
        tempList.remove(userId);
        tempEvent.setUserList(tempList);
        eventRepository.save(tempEvent);
        return ResponseEntity.ok(true);
    }
    public ResponseEntity<Boolean> unsubscribeCategory(@PathVariable int eventId, @PathVariable int categoryId) {
        Event tempEvent = eventRepository.findById(eventId);
        List<Integer> tempList = tempEvent.getCategoryList();
        tempList.remove(categoryId);
        tempEvent.setCategoryList(tempList);
        eventRepository.save(tempEvent);
        return ResponseEntity.ok(true);
    }



/*

    @GetMapping("events/search")
    public EntityModel<List<Event>> getSearchedEvents(String name,
                                                                   int categoryId,
                                                                   int sizeMin,
                                                                   int sizeMax,
                                                                   String localisation,
                                                                   int isFree,
                                                                   int isReservationNecessary,
                                                                   String ageGroupMin,
                                                                   LocalDateTime startDate,
                                                                   LocalDateTime endDate,
                                                                   boolean isFullEventIncludedInDate){
        return EntityModel.of(impl.getFilteredEvents(name,
                categoryId,
                sizeMin,
                sizeMax,
                localisation,
                isFree,
                isReservationNecessary,
                ageGroupMin,
                startDate,
                endDate,
                isFullEventIncludedInDate));
    }

    public List<Event> getEditedEvents() {
        return impl.getEditedEvents();
    }*/
}
