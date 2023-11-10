package com.example.springboot.User;

import com.example.springboot.Category.CategoryController;
import com.example.springboot.Event.AgeGroup;
import com.example.springboot.Event.EventController;
import com.example.springboot.Event.EventStatus;
import com.example.springboot.User.Exceptions.UserExistsEx;
import com.example.springboot.User.Exceptions.UserNotFoundEx;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class UserController {
    private final UserRepository impl = new UserRepositoryImpl();
    private final EventController eventController = new EventController();
    private final CategoryController categoryController = new CategoryController();


//    @GetMapping("/users/{id}")
//    public EntityModel<User> getUser(@PathVariable int id){
//        try {
//            return EntityModel.of(impl.getUser(id));
//        } catch (UserNotFoundEx e) {
//            throw new RuntimeException(e);
//        }
//    }

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
            return EntityModel.of(impl.addUser(id, name, mail, password, PermissionLevel.UNVERIFIED_USER, new ArrayList<Integer>(), new ArrayList<Integer>()));
        } catch (UserExistsEx e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("users/{userId}")
    public EntityModel<Boolean> createEvent(@PathVariable int userId,
                                            int id,
                                            String name,
                                            List<Integer> categoryList,
                                            String description,
                                            int size,
                                            String localisation,
                                            boolean isFree,
                                            boolean isReservationNecessary,
                                            boolean isLive,
                                            AgeGroup ageGroup,
                                            LocalDateTime startDate,
                                            LocalDateTime endDate){
        try {
            return EntityModel.of(eventController.createEvent(id,
                    name,
                    userId,
                    categoryList,
                    description,
                    size,
                    localisation,
                    isFree,
                    isReservationNecessary,
                    isLive,
                    ageGroup,
                    startDate,
                    endDate).getContent() && impl.subscribeEvent(userId, id));
        } catch (UserNotFoundEx e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("users/{sea}")
    public EntityModel<Boolean> get(@PathVariable int userId,
                                            int id,
                                            String name,
                                            List<Integer> categoryList,
                                            String description,
                                            int size,
                                            String localisation,
                                            boolean isFree,
                                            boolean isReservationNecessary,
                                            boolean isLive,
                                            AgeGroup ageGroup,
                                            LocalDateTime startDate,
                                            LocalDateTime endDate){
        try {
            return EntityModel.of(eventController.createEvent(id,
                    name,
                    userId,
                    categoryList,
                    description,
                    size,
                    localisation,
                    isFree,
                    isReservationNecessary,
                    isLive,
                    ageGroup,
                    startDate,
                    endDate).getContent() && impl.subscribeEvent(userId, id));
        } catch (UserNotFoundEx e) {
            throw new RuntimeException(e);
        }
    }



}
