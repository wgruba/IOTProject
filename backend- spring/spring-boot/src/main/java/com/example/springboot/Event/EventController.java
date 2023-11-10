package com.example.springboot.Event;

import com.example.springboot.Event.Exceptions.EventExistsEx;
import com.example.springboot.Event.Exceptions.EventNotFoundEx;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class EventController {
    private final EventRepository impl = new EventRepositoryImpl();

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

    public Object getEventsByOrganiser(int id) {
        return impl.getEventsByOrganiser(id);
    }
}
