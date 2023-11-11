package com.example.springboot.Event;

import com.example.springboot.Event.Exceptions.EventExistsEx;
import com.example.springboot.Event.Exceptions.EventNotFoundEx;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class EventController {
    private final EventRepository impl = new EventRepositoryImpl();

    @GetMapping("getall")
    public EntityModel<List<Event>> getAllEvents(){
        return EntityModel.of(impl.getAllEvents());
    }

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

    public List<Event> getUsersSubscribedEvents(List<Integer> usersEventList) {
        return impl.getUsersSubscribedEvents(usersEventList);
    }

    public List<Event> getEventsByOrganiser(int id) {
        return impl.getEventsByOrganiser(id);
    }

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

    @GetMapping("events/{eventId}")
    public EntityModel<Event> getEventById(@PathVariable int eventId) {
        try {
            return EntityModel.of(impl.getEvent(eventId));
        } catch (EventNotFoundEx e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("events/{eventId}")
    public List<Integer> deleteEvent(@PathVariable int eventId) {
        try {
            Event tempEvent = impl.getEvent(eventId);
            List<Integer> tempList = tempEvent.getClientList();
            impl.deleteEvent(eventId);
            return tempList;
        } catch (EventNotFoundEx e) {
            throw new RuntimeException(e);
        }
    }

    public boolean subscribeUser(int userId, int eventId) {
    }

    public boolean unsubscribeUser(int userId, int eventId) {
    }
}
