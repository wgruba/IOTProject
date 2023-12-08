package com.example.springboot.Event;

import com.example.springboot.Category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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
    @GetMapping("/events/toAcceptance")
    public ResponseEntity<List<Event>> getEventsToAcceptance(){
        List<Event> events = eventRepository.getEventsToAcceptance();
        return ResponseEntity.ok(events);
    }
    @GetMapping("/events/fromCategories")
    public ResponseEntity<List<Event>> getEventsFromCategories(List<Integer> categoriesIds){
        List<Integer> tempEventList = new ArrayList<>();
        for(int categoryId: categoriesIds){
            List<Event> tempList = eventRepository.getEventsFromCategory(categoryId);
            for (Event event: tempList) {
                if (!tempEventList.contains(event.getId()))
                    tempEventList.add(event.getId());
            }
        }
        return ResponseEntity.ok(getEventsFromList(tempEventList));
    }
    @GetMapping("/events/recent")
    public ResponseEntity<List<Event>> getRecentEvents() {
        return ResponseEntity.ok(eventRepository.findTop10ByOrderByIdDesc());
    }
    @GetMapping("/events/getRandom")
    public ResponseEntity<List<Event>> getRandomEvents(){
        List<Integer> randomIntList = new ArrayList<>();
        Random random = new Random();
        int min = 1;
        int max = getLastEventId();
        int size=10;

        for (int i = 0; i < size; i++) {
            int randomInt = random.nextInt((max - min) + 1) + min;
            if(!randomIntList.contains(randomInt))
                randomIntList.add(randomInt);
        }
        return ResponseEntity.ok(getEventsFromList(randomIntList));
    }


    // CRUD - Update
    @PutMapping("/events/{id}")
    public Event updateEvent(@PathVariable int id, @RequestBody Event event) {
        return updateEventHelper(id, event);
    }
    public Event updateEventHelper(int eventId, Event updatedEvent){
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

    @GetMapping("/events/accept/{eventId}")
    public ResponseEntity<Boolean> acceptEvent(@PathVariable int eventId){
        try {
            Event temp = eventRepository.findById(eventId);
            temp.setEventStatus(EventStatus.ACCEPTED);
            eventRepository.save(temp);
            return ResponseEntity.ok(true);
        }
        catch (Exception e){
            return ResponseEntity.ok(false);
        }
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
        if(tempList.contains(userId))
            return ResponseEntity.ok(false);
        tempList.add(userId);
        tempEvent.setClientList(tempList);
        eventRepository.save(tempEvent);
        return ResponseEntity.ok(true);
    }
    @PatchMapping("/events/{eventId}/subscribeCategory/{categoryId}")
    public ResponseEntity<Boolean> subscribeCategory(@PathVariable int eventId, @PathVariable int categoryId) {
        Event tempEvent = eventRepository.findById(eventId);
        List<Integer> tempList = tempEvent.getCategoryList();
        if(tempList.contains(categoryId))
            return ResponseEntity.ok(false);
        tempList.add(categoryId);
        tempEvent.setCategoryList(tempList);
        eventRepository.save(tempEvent);
        return ResponseEntity.ok(true);
    }
    public ResponseEntity<Boolean> unsubscribeUser(@PathVariable Integer userId, @PathVariable int eventId) {
        // jest wywoływane przez UserController.subscribeEvent
        Event tempEvent = eventRepository.findById(eventId);
        List<Integer> tempList = tempEvent.getClientList();
        if(!tempList.contains(userId))
            return ResponseEntity.ok(false);
        tempList.remove(userId);
        tempEvent.setClientList(tempList);
        eventRepository.save(tempEvent);
        return ResponseEntity.ok(true);
    }
    @PatchMapping("/events/{eventId}/unsubscribeCategory/{categoryId}")
    public ResponseEntity<Boolean> unsubscribeCategory(@PathVariable int eventId, @PathVariable Integer categoryId) {
        Event tempEvent = eventRepository.findById(eventId);
        List<Integer> tempList = tempEvent.getCategoryList();
        if(!tempList.contains(categoryId))
            return ResponseEntity.ok(false);
        tempList.remove(categoryId);
        tempEvent.setCategoryList(tempList);
        eventRepository.save(tempEvent);
        return ResponseEntity.ok(true);
    }


    @GetMapping("/events/filter")
    public ResponseEntity<List<Event>> getAllFilteredCategories(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") List<Integer> categoryList,
            @RequestParam(required = false, defaultValue = "") String localisation,
            @RequestParam(required = false, defaultValue = "") String startDate,
            @RequestParam(required = false, defaultValue = "") String endDate,
            @RequestParam(required = false) Boolean isFinished,
            @RequestParam(required = false) Integer reservation,
            @RequestParam(required = false) Integer isFree,
            @RequestParam(required = false, defaultValue = "FAMILY_FRIENDLY") AgeGroup ageGroup
    )
    {
        List<Event> allEvents = getEventsFromCategories(categoryList).getBody();
        List<Event> filteredEvents = new ArrayList<>();

        for(Event event: allEvents){
            if(event.getName().toLowerCase().contains(name.toLowerCase()))
                if(event.getLocalisation().toLowerCase().contains(localisation.toLowerCase()))
                    if(event.getEndDate().isAfter(LocalDateTime.now()) && !isFinished || event.getEndDate().isBefore(LocalDateTime.now()) && isFinished)
                        if(reservation == 0 || (event.isReservationNecessary() && reservation == 1) || (!event.isReservationNecessary() && reservation == 2))
                            if(isFree == 0 || (event.isFree() && isFree == 1) || (!event.isFree() && isFree == 2))
                                if(ageGroup == AgeGroup.FAMILY_FRIENDLY ||
                                        ageGroup == AgeGroup.OVER12 && (event.getAgeGroup() == AgeGroup.OVER12 || event.getAgeGroup() == AgeGroup.OVER12 || event.getAgeGroup() == AgeGroup.OVER16 || event.getAgeGroup() == AgeGroup.OVER18) ||
                                        ageGroup == AgeGroup.OVER16 && (event.getAgeGroup() == AgeGroup.OVER12 || event.getAgeGroup() == AgeGroup.OVER16 || event.getAgeGroup() == AgeGroup.OVER18) ||
                                        ageGroup == AgeGroup.OVER18 && (event.getAgeGroup() == AgeGroup.OVER16 || event.getAgeGroup() == AgeGroup.OVER18)){
                                    LocalDateTime startDateTime, endDateTime;
                                    if(startDate.equals(""))
                                        if(endDate.equals(""))
                                            filteredEvents.add(event);
                                        else{
                                            endDateTime = LocalDateTime.parse(endDate);
                                            if(endDateTime.isAfter(event.getEndDate()))
                                                filteredEvents.add(event);
                                        }
                                    else {
                                        startDateTime = LocalDateTime.parse(startDate);
                                        if(startDateTime.isBefore(event.getStartDate()))
                                            if (endDate.equals("")) {
                                                filteredEvents.add(event);
                                            }
                                            else {
                                                endDateTime = LocalDateTime.parse(endDate);
                                                if (endDateTime.isAfter(event.getEndDate())) {
                                                    filteredEvents.add(event);
                                                }
                                            }
                                    }

                                filteredEvents.add(event);

                            }
        }

        return ResponseEntity.ok(filteredEvents);
    }


//    @GetMapping("/events/search")
//    public ResponseEntity<List<Event>> searchForEvents(String name,
//                                                       int categoryId,
//                                                       int sizeMin,
//                                                       int sizeMax,
//                                                       String localisation,
//                                                       int isFree,
//                                                       int isReservationNecessary,
//                                                       String ageGroupMin,
//                                                       LocalDateTime startDate,
//                                                       LocalDateTime endDate,
//                                                       boolean isFullEventIncludedInDate){
//        List<Event> tempList = eventRepository.findAll();
//
//
//    }


/*
    boolean isFullEventIncludedInDate(int id, LocalDateTime startDate, LocalDateTime endDate) throws EventNotFoundEx;*/
}
