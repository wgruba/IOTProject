package com.example.springboot.User;

import com.example.springboot.User.Exceptions.UserExistsEx;
import com.example.springboot.User.Exceptions.UserNotFoundEx;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.Optional;


import java.util.List;

public interface UserRepository extends MongoRepository<User, Integer> {

    List<User> findAll();
    User findById(int id);

    @Query("{'$or': [{'name': ?0}, {'mail': ?0}]}")
    Optional<User> findByNameOrMail(String nameOrMail);

    @Query("{'$or': [{'name': ?0}, {'mail': ?0}]}")
    Optional<User> login(String nameOrMail, String password) throws UserNotFoundEx;

//    List<User> getUsersSubscribedToEvent(List<Integer> ids);

   /* //CRUD
    User getUser(int id) throws UserNotFoundEx;
    User getUser(String nameOrMail) throws UserNotFoundEx;
    boolean updateUser(int id, String name, String mail, String password, PermissionLevel permissionLevel, List<Integer> subscribedEvents, List<Integer> subscribedCategories) throws UserNotFoundEx;
    boolean deleteUser(int id) throws UserNotFoundEx;
    boolean addUser(int id, String name, String mail, String password, PermissionLevel permissionLevel, List<Integer> subscribedEvents, List<Integer> subscribedCategories) throws UserExistsEx;
    boolean addUser(User user) throws UserExistsEx;


    int countUsers();
    PermissionLevel getPermissionLevel(int id) throws UserNotFoundEx;


    //Subscription management
    boolean subscribeEvent(int userId, int eventId) throws UserNotFoundEx;
    boolean subscribeCategory(int userId, int categoryId) throws UserNotFoundEx;
    boolean unsubscribeEvent(int userId, int eventId) throws UserNotFoundEx;
    boolean unsubscribeCategory(int userId, int categoryId) throws UserNotFoundEx;


    //User's saved events and categories
    int countUsersEvents(int id) throws UserNotFoundEx;
    int countUsersCategories(int id) throws UserNotFoundEx;
    List<Integer> getUsersEventList(int id) throws UserNotFoundEx;
    List<Integer> getUsersCategoryList(int id) throws UserNotFoundEx;


    //Account management
    boolean login(String nameOrMail, String password) throws UserNotFoundEx;
    boolean remindPassword(String nameOrMail) throws UserNotFoundEx;
    boolean changePermissionLevel(int id, PermissionLevel permissionLevel) throws UserNotFoundEx;*/
}
