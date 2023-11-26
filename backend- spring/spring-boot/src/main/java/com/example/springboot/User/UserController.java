package com.example.springboot.User;

import com.example.springboot.Category.Category;
import com.example.springboot.Category.CategoryController;
import com.example.springboot.Event.AgeGroup;
import com.example.springboot.Event.Event;
import com.example.springboot.Event.EventController;
import com.example.springboot.Event.EventStatus;
import com.example.springboot.User.Exceptions.NotEnoughHighPermissionLevel;
import com.example.springboot.User.Exceptions.UserExistsEx;
import com.example.springboot.User.Exceptions.UserNotFoundEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    private final EventController eventController = new EventController();
    private final CategoryController categoryController = new CategoryController();


    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int id) {
        User user = userRepository.findById(id);
        UserDTO userDTO = UserDTO.toDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("users")
    public CollectionModel<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return CollectionModel.of(users);
    }


    @GetMapping("/users/name/{nameOrMail}")
    public User getUserByName(@PathVariable String nameOrMail) throws UserNotFoundEx {
        return userRepository.findByNameOrMail(nameOrMail)
                .orElseThrow(() -> new UserNotFoundEx(nameOrMail));
    }

       public void addSampleUser() {
        User user = new User();
        user.setMail("Wojciech@mail.com");
        user.setName("Wojciech");
        user.setPassword("password");
        user.setPermissionLevel(PermissionLevel.VERIFIED_USER);
        user.setSubscribedCategories(Collections.emptyList());
        userRepository.save(user);
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody User user) {
        userRepository.save(user);
        return "User added";
    }

/*    @PatchMapping("users/{userId}/createEvent")
    public ResponseEntity<Boolean> createEvent(@RequestBody int userId,
                                            int id,
                                            String name,
                                            List<Integer> categoryList,
                                            String description,
                                            int size,
                                            String localisation,
                                            boolean isFree,
                                            boolean isReservationNecessary,
                                            AgeGroup ageGroup,
                                            LocalDateTime startDate,
                                            LocalDateTime endDate,
                                            String imageURL){
        try {
            ArrayList<Integer> clientList = new ArrayList<>();
            return eventController.addEvent(new Event(
                    id,
                    name,
                    userId,
                    categoryList,
                    clientList,
                    description,
                    size,
                    localisation,
                    isFree,
                    isReservationNecessary,
                    ageGroup,
                    startDate,
                    endDate,
                    EventStatus.ACCEPTED,
                    imageURL)));
        } catch (UserNotFoundEx e) {
            throw new RuntimeException(e);
        }
    }*/


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



    @GetMapping("users/register")
    public EntityModel<Boolean> tryToRegisterUser(int id, String name, String mail, String password){
        try {
            //todo weryfikaja czy dane są odpowiednie
            return EntityModel.of(impl.addUser(id, name, mail, password, PermissionLevel.UNVERIFIED_USER, new ArrayList<>(), new ArrayList<>()));
        } catch (UserExistsEx e) {
            throw new RuntimeException(e);
        }
    }*/


/*
    @PatchMapping("users/{userId}/{eventId}/deleteEvent")
    public EntityModel<Boolean> deleteEvent(@PathVariable int userId, @PathVariable int eventId){
        try {
            List<Integer> ids = eventController.deleteEvent(eventId);
            for(int id:ids){
                unsubscribeEvent(id, eventId);
            }
            return EntityModel.of(impl.unsubscribeEvent(userId, eventId));
        } catch (UserNotFoundEx e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("users/{userId}/myEvents")
    public EntityModel<List<Event>> getEventsOrganisedByUser(@PathVariable int userId){
        return EntityModel.of(eventController.getEventsByOrganiser(userId));
    }



    // Subscription management
    @PatchMapping("users/{userId}/subscribeEvent/{eventId}")
    public EntityModel<Boolean> subscribeEvent(@PathVariable int userId, @PathVariable int eventId){
        try {
            return EntityModel.of(impl.subscribeEvent(userId, eventId) && eventController.subscribeUser(userId, eventId));
        } catch (UserNotFoundEx e) {
            throw new RuntimeException(e);
        }
    }
    @PatchMapping("users/{userId}/subscribeCategory/{eventId}")
    public EntityModel<Boolean> subscribeCategory(@PathVariable int userId, @PathVariable int categoryId){
        try {
            return EntityModel.of(impl.unsubscribeCategory(userId, categoryId));
        } catch (UserNotFoundEx e) {
            throw new RuntimeException(e);
        }
    }
    @PatchMapping("users/{userId}/unsubscribeEvent/{eventId}")
    public EntityModel<Boolean> unsubscribeEvent(@PathVariable int userId, @PathVariable int eventId){
        try {
            return EntityModel.of(impl.unsubscribeEvent(userId, eventId) && eventController.unsubscribeUser(userId, eventId));
        } catch (UserNotFoundEx e) {
            throw new RuntimeException(e);
        }
    }
    @PatchMapping("users/{userId}/unsubscribeCategory/{eventId}")
    public EntityModel<Boolean> unsubscribeCategory(@PathVariable int userId, @PathVariable int categoryId){
        try {
            return EntityModel.of(impl.unsubscribeCategory(userId, categoryId));
        } catch (UserNotFoundEx e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("users/{userId}/getEvents")
    public EntityModel<List<Event>> getSubscribedEvents(@PathVariable int userId){
        try {
            return EntityModel.of(eventController.getUsersSubscribedEvents(impl.getUsersEventList(userId)));
        } catch (UserNotFoundEx e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("users/{userId}/subscribedCategories")
    public EntityModel<List<Category>> getClientCategories(@PathVariable int userId){
        try {
            return EntityModel.of(categoryController.getUsersSubscribedCategories(impl.getUsersEventList(userId)));
        } catch (UserNotFoundEx e) {
            throw new RuntimeException(e);
        }
    }


    // moderator operations
    @GetMapping("users/{userId}/editedEvents")
    public EntityModel<List<Event>> getEditedEvents(@PathVariable int userId){
        try {
            PermissionLevel tempPermission = impl.getPermissionLevel(userId);
            if(tempPermission == PermissionLevel.ADMIN || tempPermission == PermissionLevel.MODERATOR){
                return EntityModel.of(eventController.getEditedEvents());
            }else{
                throw new NotEnoughHighPermissionLevel(tempPermission);
            }

        } catch (UserNotFoundEx e) {
            throw new RuntimeException(e);
        } catch (NotEnoughHighPermissionLevel e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("users/{userId}/createCategory")
    public EntityModel<Boolean> createCategory(@PathVariable int userId,
                                               int id,
                                               String name,
                                               boolean isParentCategory,
                                               List<Integer> subcategories,
                                               int parentId){
        try {
            PermissionLevel tempPermission = impl.getPermissionLevel(userId);
            if(tempPermission == PermissionLevel.ADMIN || tempPermission == PermissionLevel.MODERATOR){
                return EntityModel.of(categoryController.createCategory(id, name, isParentCategory, subcategories, parentId));
            }else{
                throw new NotEnoughHighPermissionLevel(tempPermission);
            }

        } catch (UserNotFoundEx e) {
            throw new RuntimeException(e);
        } catch (NotEnoughHighPermissionLevel e) {
            throw new RuntimeException(e);
        }
    }*/

}
