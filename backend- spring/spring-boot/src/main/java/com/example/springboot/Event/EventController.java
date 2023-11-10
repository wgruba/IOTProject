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
                                                            boolean isLive,
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
                    isLive,
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
}
