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
import org.springframework.data.mongodb.core.mapping.Document;
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
import java.util.stream.Collectors;

import static com.mongodb.client.model.Aggregates.limit;


@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    private final EventController eventController = new EventController();
    private final CategoryController categoryController = new CategoryController();

    // CRUD - Create
    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        Optional<User> existingUser = userRepository.getUserByNameOrMail(user.getName());
        if (existingUser.isPresent()) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("User with the same name or email already exists");
        }
        userRepository.save(user);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/last")
    public int getLastUser() {
        return userRepository.findTopByOrderByIdDesc()
                .map(User::getId)
                .orElse(null);
    }

    // CRUD - Read
    @GetMapping("users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = users.stream()
                .map(UserDTO::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int id) throws UserNotFoundEx{
        try {
            User user = userRepository.getUserById(id)
                    .orElseThrow(() -> new UserNotFoundEx(id));
            return ResponseEntity.ok(UserDTO.toDTO(user));
        } catch (Exception e) {
            throw new UserNotFoundEx(id);
        }
    }
    @GetMapping("/users/name/{nameOrMail}")
    public UserDTO getUserByName(@PathVariable String nameOrMail) throws UserNotFoundEx {
        User user = userRepository.getUserByNameOrMail(nameOrMail)
                .orElseThrow(() -> new UserNotFoundEx(nameOrMail));
        return UserDTO.toDTO(user);
    }
    @GetMapping("/users/list")
    public List<User> getUsersFromList(ArrayList<Integer> idList) {
        return userRepository.getUsersSubscribedToEvent(idList);
    }

    // CRUD - Update
    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User user) {
        return updateUser2(id, user);
    }
    public User updateUser2(int userId, User updatedUser) {
        return userRepository.findById(userId).map(user -> {
            user.setName(updatedUser.getName());
            user.setMail(updatedUser.getMail());
            user.setName(updatedUser.getName());
            user.setPassword(updatedUser.getPassword());
            user.setPermissionLevel(updatedUser.getPermissionLevel());
            user.setSubscribedEvents(updatedUser.getSubscribedEvents());
            user.setSubscribedCategories(updatedUser.getSubscribedCategories());
            return userRepository.save(user);
        }).orElseGet(() -> {
            updatedUser.setId(userId);
            return userRepository.save(updatedUser);
        });
    }

    // CRUD - Delete
    @DeleteMapping("/users/delete/{id}")
    public boolean deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
        return true;
    }


    public boolean loginUser(String loginOrMail, String password) {
        Optional<User> userOptional = userRepository.getUserByNameOrMail(loginOrMail);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return password.equals(user.getPassword());
        }
        return false;
    }

    @GetMapping("users/login")
    public ResponseEntity<Boolean> tryToLoginUser(String loginOrMail, String password){
        try {
            boolean result = userRepository.login(loginOrMail, password).isPresent();
            return ResponseEntity.ok(result); // trzeba dodać user ID żeby się wysyłało
        } catch (UserNotFoundEx e) {
            throw new RuntimeException(e);
        }
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