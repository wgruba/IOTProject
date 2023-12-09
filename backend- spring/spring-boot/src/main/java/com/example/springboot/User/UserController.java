package com.example.springboot.User;

import com.example.springboot.Category.Category;
import com.example.springboot.Category.CategoryController;
import com.example.springboot.Event.Event;
import com.example.springboot.Event.EventController;
//import com.example.springboot.User.Exceptions.UserNotFoundEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    // CRUD - Create
    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        Optional<User> existingUser = userRepository.getUserByNameOrMail(user.getName());
        if (existingUser.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("User with the same name or email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
    @GetMapping("/users/filter")
    public ResponseEntity<List<UserDTO>> getAllUsersFiltered(
        @RequestParam(required = false, defaultValue = "") String name,
        @RequestParam(required = false, defaultValue = "") String mail,
        @RequestParam boolean admin,
        @RequestParam boolean mod,
        @RequestParam boolean ver,
        @RequestParam boolean nonver
        )
    {
        List<User> users = userRepository.findAll();
        List<User> filteredUsers = new ArrayList<>();
        for(User user: users){
            int tot = 0;
            if(user.getName().toLowerCase().contains(name.toLowerCase()))
                tot++;
            if(user.getMail().toLowerCase().contains(mail.toLowerCase()))
                tot++;
            if(user.getPermissionLevel() == PermissionLevel.ADMIN && admin)
                tot++;
            if(user.getPermissionLevel() == PermissionLevel.MODERATOR && mod)
                tot++;
            if(user.getPermissionLevel() == PermissionLevel.VERIFIED_USER && ver)
                tot++;
            if(user.getPermissionLevel() == PermissionLevel.UNVERIFIED_USER && nonver)
                tot++;
            if(tot == 3)
                filteredUsers.add(user);
        }
        List<UserDTO> userDTOs = filteredUsers.stream()
                .map(UserDTO::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int id){
        User user = userRepository.getUserById(id).get();
        return ResponseEntity.ok(UserDTO.toDTO(user));
    }
    @GetMapping("/users/name/{nameOrMail}")
    public UserDTO getUserByName(@PathVariable String nameOrMail) {
        User user = userRepository.getUserByNameOrMail(nameOrMail).get();
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
    @PutMapping("/users/{id}/updatePermissions")
    public User updateUserPermissions(@PathVariable Integer id, @RequestBody Integer permissionLevel) {
        PermissionLevel temp = null;
        switch (permissionLevel) {
            case 4:
                temp = PermissionLevel.UNVERIFIED_USER;
                break;
            case 3:
                temp = PermissionLevel.VERIFIED_USER;
                break;
            case 2:
                temp = PermissionLevel.MODERATOR;
                break;
            case 1:
                temp = PermissionLevel.ADMIN;
                break;
            default:
                break;
        }
        final PermissionLevel finalTemp = temp;
        return userRepository.findById(id).map(user -> {
            user.setPermissionLevel(finalTemp);
            return userRepository.save(user);
        }).orElseGet(() -> {
            return null;
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
    @GetMapping("users/login")
    public ResponseEntity<Boolean> tryToLoginUser(String loginOrMail, String password){
        boolean result = userRepository.login(loginOrMail, password).isPresent();
        return ResponseEntity.ok(result);
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
    public ResponseEntity<String> getUsersName(@PathVariable int userId){
        User tempUser = userRepository.getUserById(userId).get();
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
        if(tempList.contains(eventId))
            return ResponseEntity.ok(false);
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
        if(tempList.contains(categoryId))
            return ResponseEntity.ok(false);
        tempList.add(categoryId);
        tempUser.setSubscribedCategories(tempList);
        userRepository.save(tempUser);
        return ResponseEntity.ok(true);
    }
    @PatchMapping("/users/{userId}/subscribeCategories")
    public ResponseEntity<Boolean> subscribeCategory(@PathVariable int userId, @RequestBody List<Integer> categoryList) {
        User tempUser = userRepository.findById(userId).get();
        List<Integer> tempList = tempUser.getSubscribedCategories();

        for(Integer categoryId: categoryList)
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
        if(!tempList.contains(eventId))
            return ResponseEntity.ok(false);
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
        if(!tempList.contains(categoryId))
            return ResponseEntity.ok(false);
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


    // Moderator options
    @PostMapping("/users/{userId}/createCategory")
    public ResponseEntity<Category> createUser(@PathVariable int userId, @RequestBody Category category){
        ResponseEntity<Category> tempCategory = (ResponseEntity<Category>) categoryController.addCategory(category);
        return tempCategory;
    }
    @PatchMapping("/users/{userId}/updateCategory")
    public ResponseEntity<?> updateCategory(@PathVariable int userId, @RequestBody Category category){
        return ResponseEntity.ok(categoryController.updateCategory(category.getId(), category));
    }
}