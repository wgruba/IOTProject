package com.example.springboot.User;

import com.example.springboot.User.Exceptions.UserExistsEx;
import com.example.springboot.User.Exceptions.UserNotFoundEx;
import com.example.springboot.Event.Event;
import com.example.springboot.Category.Category;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();

    //CRUD
    User getUser(int id) throws UserNotFoundEx;
    User getUser(String nameOrMail) throws UserNotFoundEx;
    User updateUser(int id, String name, String mail, String password, PermissionLevel permissionLevel, List<Integer> subscribedEvents, List<Integer> subscribedCategories) throws UserNotFoundEx;
    boolean deleteUser(int id) throws UserNotFoundEx;
    User addUser(int id, String name, String mail, String password, PermissionLevel permissionLevel, List<Integer> subscribedEvents, List<Integer> subscribedCategories) throws UserExistsEx;
    User addUser(User user) throws UserExistsEx;


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
    boolean login(int id, String password);
    void remindPassword(String name, String mail);
    boolean changePermissionLevel(int adminId, int userId, PermissionLevel permissionLevel) throws UserNotFoundEx;
}
