package com.example.springboot.User;

import com.example.springboot.Category.Category;
import com.example.springboot.Category.CategoryController;
import com.example.springboot.Event.AgeGroup;
import com.example.springboot.Event.Event;
import com.example.springboot.Event.EventController;
import com.example.springboot.User.Exceptions.NotEnoughHighPermissionLevel;
import com.example.springboot.User.Exceptions.UserExistsEx;
import com.example.springboot.User.Exceptions.UserNotFoundEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    private final EventController eventController = new EventController();
    private final CategoryController categoryController = new CategoryController();


    @GetMapping("/users/{id}")
    public EntityModel<User> getUser(@PathVariable int id){
        try {
            return EntityModel.of(userRepository.findById(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("users")
    public CollectionModel<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return CollectionModel.of(users);
    }


    @GetMapping("/users/name/{nameOrMail}")
    public User getUserByName(@PathVariable String nameOrMail) throws UserNotFoundEx {
        return userRepository.findByNameOrMail(nameOrMail)
                .orElseThrow(() -> new UserNotFoundEx("User not found with name or email: " + nameOrMail));
    }

    public void addSampleUser() {
        User user = new User();
        user.setMail("Wojciech@mail.com");
        user.setName("Wojciech");
        user.setPassword("password");
        user.setPermissionLevel(PermissionLevel.VERIFIED_USER);
        user.setSubscribedCategories(Collections.emptyList()); // Empty list
        userRepository.save(user);
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody User user) {
        userRepository.save(user);
        return "User added";
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



    @GetMapping("users/login")
    public EntityModel<Boolean> tryToLoginUser(String loginOrMail, String password){
        try {
            return EntityModel.of(impl.login(loginOrMail, password));
        } catch (UserNotFoundEx e) {
            throw new RuntimeException(e);
        }
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
    // Events organised by me
    @PatchMapping("users/{userId}/createEvent")
    public EntityModel<Boolean> createEvent(@PathVariable int userId,
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
                                            LocalDateTime endDate){
        try {
            return EntityModel.of(Boolean.TRUE.equals(eventController.createEvent(id,
                    name,
                    userId,
                    categoryList,
                    description,
                    size,
                    localisation,
                    isFree,
                    isReservationNecessary,
                    ageGroup,
                    startDate,
                    endDate).getContent()) && impl.subscribeEvent(userId, id));
        } catch (UserNotFoundEx e) {
            throw new RuntimeException(e);
        }
    }
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
