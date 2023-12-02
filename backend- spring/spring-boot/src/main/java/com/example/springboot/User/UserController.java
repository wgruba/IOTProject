package com.example.springboot.User;

import com.example.springboot.Category.Category;
import com.example.springboot.Category.CategoryController;
import com.example.springboot.Event.Event;
import com.example.springboot.Event.EventController;
import com.example.springboot.User.Exceptions.UserNotFoundEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventController eventController;
    @Autowired
    private CategoryController categoryController;

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
    @GetMapping("/user/last")
    public int getLastUser() {
        return userRepository.findTopByOrderByIdDesc()
                .map(User::getId)
                .orElse(null);
    }


    // CRUD - Update
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User user) {
        return updateUserHelper(id, user);
    }
    public User updateUserHelper(int userId, User updatedUser) {
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
    @DeleteMapping("/users/{userId}")
    public boolean deleteUser(@PathVariable int userId) {
        User tempUser = userRepository.getUserById(userId).get();
        List<Event> tempList = eventController.getEventsByOrganiser(userId);
        for (Event event:tempList) {
            deleteEvent(userId, event.getId());
        }

        List<Integer> subscribedEvents = tempUser.getSubscribedEvents();
        for (Integer eventId: subscribedEvents) {
            unsubscribeEvent(userId, eventId);
        }

        userRepository.deleteById(userId);
        return true;
    }


    // Account management
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
    public ResponseEntity<PermissionLevel> getPermissionLevel(int userId){
        return ResponseEntity.ok(userRepository.findById(userId).get().getPermissionLevel());
    }
    public ResponseEntity<Boolean> changePermissionLevel(int userId, PermissionLevel permissionLevel){
        userRepository.findById(userId).map(user -> {
                    user.setPermissionLevel(permissionLevel);
        return ResponseEntity.ok(true);});
        return ResponseEntity.ok(false);
    }
    @GetMapping("/users/{userId}/getName")
    public ResponseEntity<String> getUsersName(@PathVariable int userid){
        User tempUser = userRepository.getUserById(userid).get();
        return ResponseEntity.ok(tempUser.getName());
    }


    // Subscription Management
    @GetMapping("/users/{userId}/subscribedEvents")
    public ResponseEntity<List<Event>> getSubscribedEvents(@PathVariable int userId) {
        User tempUser = userRepository.findById(userId).get();
        return ResponseEntity.ok(eventController.getEventsFromList(tempUser.getSubscribedEvents()));
    }
    @GetMapping("/users/{userId}/subscribedCategories")
    public ResponseEntity<List<Category>> getSubscribedCategories(@PathVariable int userId) {
        User tempUser = userRepository.findById(userId).get();
        return ResponseEntity.ok(categoryController.getCategoriesFromList(tempUser.getSubscribedCategories()));
    }
    @PatchMapping("/users/{userId}/subscribeEvent/{eventId}")
    public ResponseEntity<Boolean> subscribeEvent(@PathVariable int userId, @PathVariable int eventId) {
        User tempUser = userRepository.findById(userId).get();
        List<Integer> tempList = tempUser.getSubscribedEvents();
        tempList.add(eventId);
        tempUser.setSubscribedEvents(tempList);
        userRepository.save(tempUser);
        eventController.subscribeUser(userId, eventId);
        return ResponseEntity.ok(true);
    }
    @PatchMapping("/users/{userId}/subscribeCategory/{categoryId}")
    public ResponseEntity<Boolean> subscribeCategory(@PathVariable int userId, @PathVariable int categoryId) {
        User tempUser = userRepository.findById(userId).get();
        List<Integer> tempList = tempUser.getSubscribedCategories();
        tempList.add(categoryId);
        tempUser.setSubscribedCategories(tempList);
        userRepository.save(tempUser);
        return ResponseEntity.ok(true);
    }
    @PatchMapping("/users/{userId}/subscribeCategories")
    public ResponseEntity<Boolean> subscribeCategory(@PathVariable int userId, List<Integer> categoryList) {
        User tempUser = userRepository.findById(userId).get();
        List<Integer> tempList = tempUser.getSubscribedCategories();

        for(Integer categoryId: categoryList){
            if(!tempList.contains(categoryId))
                tempList.add(categoryId);
            
        tempUser.setSubscribedCategories(tempList);
        userRepository.save(tempUser);
        return ResponseEntity.ok(true);
    }

    @PatchMapping("/users/{userId}/unsubscribeEvent/{eventId}")
    public ResponseEntity<Boolean> unsubscribeEvent(@PathVariable int userId, @PathVariable Integer eventId) {
        if(eventController.getEventOrganiser(eventId) == userId)
            return ResponseEntity.ok(false);
        User tempUser = userRepository.findById(userId).get();
        List<Integer> tempList = tempUser.getSubscribedEvents();
        tempList.remove(eventId);
        tempUser.setSubscribedEvents(tempList);
        userRepository.save(tempUser);
        eventController.unsubscribeUser(userId, eventId);
        return ResponseEntity.ok(true);
    }
    @PatchMapping("/users/{userId}/unsubscribeCategory/{categoryId}")
    public ResponseEntity<Boolean> unsubscribeCategory(@PathVariable int userId, @PathVariable Integer categoryId) {
        User tempUser = userRepository.findById(userId).get();
        List<Integer> tempList = tempUser.getSubscribedCategories();
        tempList.remove(categoryId);
        tempUser.setSubscribedCategories(tempList);
        userRepository.save(tempUser);
        return ResponseEntity.ok(true);
    }


    // User's Events
    @PostMapping("/users/{userId}/createEvent")
    public ResponseEntity<Event> createEvent(@PathVariable int userId, @RequestBody Event event){
        ResponseEntity<Event> tempEvent = eventController.addEvent(event);
        subscribeEvent(userId, event.getId());
        return tempEvent;
    }
    @PatchMapping("/users/{userId}/deleteEvent/{eventId}")
    public ResponseEntity<Boolean> deleteEvent(@PathVariable int userId, @PathVariable int eventId){
        if(eventController.getEventOrganiser(eventId) != userId)
            return ResponseEntity.ok(false);
        List<Integer> userList = eventController.getUsersThatSubscribedToEvent(eventId);
        for(Integer user:userList){
            unsubscribeEvent(user, eventId);
        }

        eventController.deleteEvent(eventId);

        User tempUser = userRepository.findById(userId).get();
        List<Integer> tempList = tempUser.getSubscribedEvents();
        tempList.remove(eventId);
        tempUser.setSubscribedEvents(tempList);
        userRepository.save(tempUser);

        return ResponseEntity.ok(true);
    }
    @GetMapping("users/{userId}/myEvents")
    public ResponseEntity<List<Event>> getEventsOrganisedByUser(@PathVariable int userId){
        return ResponseEntity.ok(eventController.getEventsByOrganiser(userId));
    }
    @PatchMapping("/users/{userId}/updateEvent")
    public ResponseEntity<?> updateEvent(@PathVariable int userId, @RequestBody Event event){
        if(eventController.getEventOrganiser(event.getId()) != userId)
            return ResponseEntity.ok(false);
        return ResponseEntity.ok(eventController.updateEvent(event.getId(), event));
    }


    @PostMapping("/users/{userId}/createCategory")
    public ResponseEntity<Category> createUser(@PathVariable int userId, @RequestBody Category category){
        ResponseEntity<Category> tempCategory = (ResponseEntity<Category>) categoryController.addCategory(category);
        return tempCategory;
    }
    @PatchMapping("/users/{userId}/updateCategory")
    public ResponseEntity<?> updateCategory(@PathVariable int userId, @RequestBody Category category){
        return ResponseEntity.ok(categoryController.updateCategory(category.getId(), category));
    }


/*
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

    }*/

}