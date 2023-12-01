package com.example.springboot.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class EventController {

    @Autowired
    private EventRepository eventRepository;


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
    @GetMapping("/events/{eventId}")
    public ResponseEntity<Event> getEvent(@PathVariable int eventId) {
        Event event = eventRepository.findById(eventId);
        return ResponseEntity.ok(event);
    }
    @GetMapping("/events/name/{name}")
    public ResponseEntity<Event> getEventByName(@PathVariable String name) {
        Event event = eventRepository.getEventByName(name).get();
        return ResponseEntity.ok(event);
    }
    @GetMapping("/events/list")
    public List<Event>getEventsFromList(List<Integer> ids){
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
    @GetMapping("/events/last")
    public int getLastEventId() {
        return eventRepository.findTopByOrderByIdDesc()
                .map(Event::getId)
                .orElse(null);
    }


    // CRUD - Update
    @PutMapping("/events/{id}")
    public Event updateEvent(@PathVariable int id, @RequestBody Event event) {
        return updateEventHelper(id, event);
    }
    public Event updateEventHelper(int eventId, Event updatedEvent) {
        Event event = eventRepository.findById(eventId);
        event.setName(updatedEvent.getName());
        event.setOrganizer(updatedEvent.getOrganizer());
        event.setCategoryList(updatedEvent.getCategoryList());
        event.setClientList(updatedEvent.getClientList());
        event.setOrganizer(updatedEvent.getOrganizer());
        event.setDescription(updatedEvent.getDescription());
        event.setSize(updatedEvent.getSize());
        event.setLocalisation(updatedEvent.getLocalisation());
        event.setFree(updatedEvent.isFree());
        event.setReservationNecessary(updatedEvent.isReservationNecessary());
        event.setAgeGroup(updatedEvent.getAgeGroup());
        event.setStartDate(updatedEvent.getStartDate());
        event.setEndDate(updatedEvent.getEndDate());
        event.setEventStatus(updatedEvent.getEventStatus());
        event.setImageUrl(updatedEvent.getImageUrl());
        return eventRepository.save(event);
    }


    // CRUD - Delete
    @DeleteMapping("/events/{eventId}")
    public boolean deleteEvent(@PathVariable int eventId){
        eventRepository.deleteById(eventId);
        return true;
    }


    @GetMapping("/events/{eventId}/organiser")
    public int getEventOrganiser(@PathVariable int eventId){
        Event temp = eventRepository.findById(eventId);
//        return eventRepository.findById(eventId).getOrganizer();
        return temp.getOrganizer();
    }
    public List<Integer> getUsersThatSubscribedToEvent(int eventId) {
        return eventRepository.findById(eventId).getClientList();
    }


    // Subscription Management
    @GetMapping("/events/{eventId}/subscribedUsers")
    public ResponseEntity<List<Integer>> getSubscribedEvents(@PathVariable int eventId) {
        Event tempEvent = eventRepository.findById(eventId);
        return ResponseEntity.ok(tempEvent.getClientList());
    }
    @GetMapping("/events/{eventId}/subscribedCategories")
    public ResponseEntity<List<Integer>> getSubscribedCategories(@PathVariable int eventId) {
            Event tempEvent = eventRepository.findById(eventId);
            return ResponseEntity.ok(tempEvent.getCategoryList());
        }

    public ResponseEntity<Boolean> subscribeUser(@PathVariable Integer userId, @PathVariable int eventId) {
        // jest wywoływane przez UserController.subscribeEvent
        Event tempEvent = eventRepository.findById(eventId);
        List<Integer> tempList = tempEvent.getClientList();
        tempList.add(userId);
        tempEvent.setClientList(tempList);
        eventRepository.save(tempEvent);
        return ResponseEntity.ok(true);
    }
    @PatchMapping("/events/{eventId}/subscribeCategory/{categoryId}")
    public ResponseEntity<Boolean> subscribeCategory(@PathVariable int eventId, @PathVariable int categoryId) {
        Event tempEvent = eventRepository.findById(eventId);
        List<Integer> tempList = tempEvent.getCategoryList();
        tempList.add(categoryId);
        tempEvent.setCategoryList(tempList);
        eventRepository.save(tempEvent);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<Boolean> unsubscribeUser(@PathVariable Integer userId, @PathVariable int eventId) {
        // jest wywoływane przez UserController.subscribeEvent
        Event tempEvent = eventRepository.findById(eventId);
        List<Integer> tempList = tempEvent.getClientList();
        tempList.remove(userId);
        tempEvent.setClientList(tempList);
        eventRepository.save(tempEvent);
        return ResponseEntity.ok(true);
    }
    @PatchMapping("/events/{eventId}/unsubscribeCategory/{categoryId}")
    public ResponseEntity<Boolean> unsubscribeCategory(@PathVariable int eventId, @PathVariable Integer categoryId) {
        Event tempEvent = eventRepository.findById(eventId);
        List<Integer> tempList = tempEvent.getCategoryList();
        tempList.remove(categoryId);
        tempEvent.setCategoryList(tempList);
        eventRepository.save(tempEvent);
        return ResponseEntity.ok(true);
    }



    /*
     *//***
     * usage: main page; giving list of random events
     * @return list of some events for user
     *//*
    @GetMapping
    public EntityModel<List<Event>> getRandomEvents(){
        //todo
        return null;
    }
    /*
    @GetMapping("users/{userId}")
    public EntityModel<Boolean> createEvent(int id,
                                                            String name,
                                                            int organizer,
                                                            List<Integer> categoryList,
                                                            String description,
                                                            int size,
                                                            String localisation,
                                                            boolean isFree,
                                                            boolean isReservationNecessary,
                                                            AgeGroup ageGroup,
                                                            LocalDateTime startDate,
                                                            LocalDateTime endDate)
    {
        try {
            return EntityModel.of(impl.addEvent(id,
                    name,
                    organizer,
                    categoryList,
                    new ArrayList<Integer>(),
                    description,
                    size,
                    localisation,
                    isFree,
                    isReservationNecessary,
                    ageGroup,
                    startDate,
                    endDate,
                    EventStatus.ACCEPTED) && impl.subscribeUser(id, organizer));
        } catch (EventExistsEx e) {
            throw new RuntimeException(e);
        } catch (EventNotFoundEx e) {
            throw new RuntimeException(e);
        }
    }

    *//***
     * getUsersSubscribedEvents- returns list of events, that user has subscribed to
     *
     * @param usersEventList
     * @return
     *//*
    public List<Event> getUsersSubscribedEvents(List<Integer> usersEventList) {
        return impl.getUsersSubscribedEvents(usersEventList);
    }

    public List<Event> getEventsByOrganiser(int id) {
        return impl.getEventsByOrganiser(id);


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
