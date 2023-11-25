package com.example.springboot.User;

import com.example.springboot.User.Exceptions.UserExistsEx;
import com.example.springboot.User.Exceptions.UserNotFoundEx;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import java.util.Optional;


import java.util.List;

public interface UserRepository extends MongoRepository<User, Integer> {

    //CRUD - Create
    User save(User user);

    //CRUD - Read
    List<User> findAll();
    @Query("{'id': ?0}")
    Optional<User> getUserById(int userId);
    @Query("{'$or': [{'name': ?0}, {'mail': ?0}]}")
    Optional<User> getUserByNameOrMail(String nameOrMail);
    @Query("{'id': {'$in': ?0}}")
    List<User> getUsersSubscribedToEvent(@Param("ids") List<Integer> ids);


    // CRUD - Delete
    void deleteById(int id);



    @Query("{'$or': [{'name': ?0}, {'mail': ?0}]}")
    Optional<User> login(String nameOrMail, String password) throws UserNotFoundEx;



   /* //CRUD
    boolean updateUser(int id, String name, String mail, String password, PermissionLevel permissionLevel, List<Integer> subscribedEvents, List<Integer> subscribedCategories) throws UserNotFoundEx;



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
