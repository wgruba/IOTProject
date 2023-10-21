package com.example.springboot.User;

import com.example.springboot.Category.Category;
import com.example.springboot.User.Exceptions.UserExistsEx;
import com.example.springboot.User.Exceptions.UserNotFoundEx;
import com.example.springboot.Event.Event;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private List<User> userList;
    public UserRepositoryImpl(){
        userList = new ArrayList<User>();

        userList.add(new User(1, "user1", "example@example", "123", PermissionLevel.UNVERIFIED_USER, new ArrayList<Integer>(), new ArrayList<Integer>()));
        userList.add(new User(2, "user2", "example@example", "123", PermissionLevel.VERIFIED_USER, new ArrayList<Integer>(), new ArrayList<Integer>()));
        userList.add(new User(3, "user3", "example@example", "123", PermissionLevel.ADMIN, new ArrayList<Integer>(), new ArrayList<Integer>()));
        userList.add(new User(0, "user0", "example@example", "123", PermissionLevel.MODERATOR, new ArrayList<Integer>(), new ArrayList<Integer>()));
    }

    @Override
    public List<User> getAllUsers() {
        return userList;
    }

    @Override
    public User getUser(int id) throws UserNotFoundEx {
        for(User theUser:userList){
            if(theUser.getId() == id){
                return theUser;
            }
        }
        throw new UserNotFoundEx(id);
    }

    @Override
    public User getUser(String nameOrMail) throws UserNotFoundEx {
        for(User theUser:userList){
            if(theUser.getName().equals(nameOrMail) || theUser.getMail().equals(nameOrMail)){
                return theUser;
            }
        }
        throw new UserNotFoundEx(nameOrMail);
    }

    @Override
    public User updateUser(int id,
                             String name,
                             String mail,
                             String password,
                             PermissionLevel permissionLevel,
                             List<Integer> subscribedEvents,
                             List<Integer> subscribedCategories)
            throws UserNotFoundEx {
        for(User theUser:userList){
            if(theUser.getId() == id){
                theUser.setName(name);
                theUser.setMail(mail);
                theUser.setPassword(password);
                theUser.setPermissionLevel(permissionLevel);
                theUser.setSubscribedEvents(subscribedEvents);
                theUser.setSubscribedCategories(subscribedCategories);
            }
        }
        throw new UserNotFoundEx(id);
    }

    @Override
    public boolean deleteUser(int id) throws UserNotFoundEx {
        for(User theUser:userList){
            if(theUser.getId() == id){
                userList.remove(theUser);
                return true;
            }
        }
        throw new UserNotFoundEx(id);
    }

    @Override
    public User addUser(int id,
                          String name,
                          String mail,
                          String password,
                          PermissionLevel permissionLevel,
                          List<Integer> subscribedEvents,
                          List<Integer> subscribedCategories)
            throws UserExistsEx {
        for(User theUser:userList){
            if(theUser.getId() == id){
                throw new UserExistsEx(id);
            }
        }
        User user = new User(id, name, mail, password, permissionLevel, subscribedEvents, subscribedCategories);
        userList.add(user);
        return user;
    }

    @Override
    public User addUser(User user) throws UserExistsEx {
        int id = user.getId();
        for(User theUser:userList){
            if(theUser.getId() == id){
                throw new UserExistsEx(id);
            }
        }
        userList.add(user);
        return user;
    }

    @Override
    public int countUsers() {
        return userList.size();
    }

    @Override
    public PermissionLevel getPermissionLevel(int id) throws UserNotFoundEx {
        for(User theUser:userList){
            if(theUser.getId() == id){
                return theUser.getPermissionLevel();
            }
        }
        throw new UserNotFoundEx(id);
    }

    @Override
    public boolean subscribeEvent(int id, Event event) throws UserNotFoundEx {
        for(User theUser:userList){
            if(theUser.getId() == id){
                List<Integer> subscribedEvents = theUser.getSubscribedEvents();
                subscribedEvents.add(event.getId());
                theUser.setSubscribedEvents(subscribedEvents);
                return true;
            }
        }
        throw new UserNotFoundEx(id);
    }

    @Override
    public boolean subscribeCategory(int id, Category category) throws UserNotFoundEx {
        for(User theUser:userList){
            if(theUser.getId() == id){
                List<Integer> subscribedCategories = theUser.getSubscribedCategories();
                subscribedCategories.add(category.getId());
                theUser.setSubscribedCategories(subscribedCategories);
                return true;
            }
        }
        throw new UserNotFoundEx(id);
    }

    @Override
    public boolean unsubscribeEvent(int id, Event event) throws UserNotFoundEx {
        for(User theUser:userList){
            if(theUser.getId() == id){
                List<Integer> subscribedEvents = theUser.getSubscribedEvents();
                subscribedEvents.remove(event.getId());
                theUser.setSubscribedEvents(subscribedEvents);
                return true;
            }
        }
        throw new UserNotFoundEx(id);
    }

    @Override
    public boolean unsubscribeCategory(int id, Category category) throws UserNotFoundEx {
        for(User theUser:userList){
            if(theUser.getId() == id){
                List<Integer> subscribedCategories = theUser.getSubscribedCategories();
                subscribedCategories.remove(category.getId());
                theUser.setSubscribedCategories(subscribedCategories);
                return true;
            }
        }
        throw new UserNotFoundEx(id);
    }

    @Override
    public int countUsersEvents(int id) throws UserNotFoundEx {
        for(User theUser:userList){
            if(theUser.getId() == id){
                return theUser.getSubscribedEvents().size();
            }
        }
        throw new UserNotFoundEx(id);
    }

    @Override
    public int countUsersCategories(int id) throws UserNotFoundEx {
        for(User theUser:userList){
            if(theUser.getId() == id){
                return theUser.getSubscribedCategories().size();
            }
        }
        throw new UserNotFoundEx(id);
    }

    @Override
    public List<Integer> getUsersEventList(int id) throws UserNotFoundEx {
        for(User theUser:userList){
            if(theUser.getId() == id){
                return theUser.getSubscribedEvents();
            }
        }
        throw new UserNotFoundEx(id);
    }

    @Override
    public List<Integer> getUsersCategoryList(int id) throws UserNotFoundEx {
        for(User theUser:userList){
            if(theUser.getId() == id){
                return theUser.getSubscribedCategories();
            }
        }
        throw new UserNotFoundEx(id);
    }

    @Override
    public boolean login(int id, String password) {
        //todo
        return false;
    }

    @Override
    public void remindPassword(String name, String mail) {
        //todo jakieś wysyłanie maila z przypomnieniem
    }

    @Override
    public boolean changePermissionLevel(int id, PermissionLevel permissionLevel) throws UserNotFoundEx {
        for(User theUser:userList){
            if(theUser.getId() == id){
                theUser.setPermissionLevel(permissionLevel);
                return true;
            }
        }
        throw new UserNotFoundEx(id);
    }

}
