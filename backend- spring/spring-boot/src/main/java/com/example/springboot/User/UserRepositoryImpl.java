package com.example.springboot.User;

import com.example.springboot.User.Exceptions.UserExistsEx;
import com.example.springboot.User.Exceptions.UserNotFoundEx;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    public UserRepositoryImpl(){}

    @Override
    public List<User> getAllUsers() {
        System.out.println("getAllUsers" );
        //todo link to Agata's function: Users Read bez_filtra
        return null;
    }

    @Override
    public List<User> getUsersSubscribedToEvent(List<Integer> ids) {
        System.out.println("getUsersSubscribedToEvent"  + ids);
        //todo link to Agata's function: Users Read filtr in("id", ids)
        return null;
    }

    @Override
    public User getUser(int id) throws UserNotFoundEx {
        System.out.println("getUser"  + id);
        //todo link to Agata's function: Users Read filtr eq("id" = id)
        return null;
    }

    @Override
    public User getUser(String nameOrMail) throws UserNotFoundEx {
        System.out.println("getUser"  + nameOrMail);
        //todo link to Agata's function: Users Read filtr or(eq("mail" = nameOrMail), eq("name" = nameOrMail)
        return null;
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
        System.out.println("updateUser: " + id + " " + name + " " + mail + " " + password + " " + permissionLevel + " " + subscribedEvents + " " + subscribedCategories);
        //todo link to Agata's function: Users Update filtr eq("id" = id)
        return null;
    }

    @Override
    public boolean deleteUser(int id) throws UserNotFoundEx {
        System.out.println("deleteUser: " + id);
        //todo link to Agata's function: Users Delete filtr eq("id" = id)
        return true;
    }

    @Override
    public boolean addUser(int id,
                           String name,
                           String mail,
                           String password,
                           PermissionLevel permissionLevel,
                           List<Integer> subscribedEvents,
                           List<Integer> subscribedCategories)
            throws UserExistsEx {
        System.out.println("addUser: " + id + " " + name + " " + mail + " " + password + " " + permissionLevel + " " + subscribedEvents + " " + subscribedCategories);
        //todo link to Agata's function: Users Create
        return false;
    }

    @Override
    public boolean addUser(User user) throws UserExistsEx {
        System.out.println("addUser: " + user);
        //todo link to Agata's function: Users Create
        return false;
    }

    @Override
    public int countUsers() {
        System.out.println("countUsers");
        return getAllUsers().size();
    }

    @Override
    public PermissionLevel getPermissionLevel(int id) throws UserNotFoundEx {
        System.out.println("getPermissionLevel: " + id);
        return getUser(id).getPermissionLevel();
    }

    @Override
    public boolean subscribeEvent(int userId, int eventId) throws UserNotFoundEx {
        System.out.println("subscribeEvent: " + userId + " " + eventId);

        User tempUser = getUser(userId);
        List<Integer> tempList = tempUser.getSubscribedEvents();
        tempList.add(eventId);

        updateUser(userId,
                tempUser.getName(),
                tempUser.getMail(),
                tempUser.getPassword(),
                tempUser.getPermissionLevel(),
                tempList,
                tempUser.getSubscribedCategories());

        return true;
    }

    @Override
    public boolean subscribeCategory(int userId, int categoryId) throws UserNotFoundEx {
        System.out.println("subscribeCategory: " + userId + " " + categoryId);
        User tempUser = getUser(userId);
        List<Integer> tempList = tempUser.getSubscribedCategories();
        tempList.add(categoryId);

        updateUser(userId,
                tempUser.getName(),
                tempUser.getMail(),
                tempUser.getPassword(),
                tempUser.getPermissionLevel(),
                tempUser.getSubscribedEvents(),
                tempList);
        return true;
    }

    @Override
    public boolean unsubscribeEvent(int userId, int eventId) throws UserNotFoundEx {
        System.out.println("unsubscribeEvent: " + userId + " " + eventId);

        User tempUser = getUser(userId);
        List<Integer> tempList = tempUser.getSubscribedEvents();
        tempList.remove(eventId);

        updateUser(userId,
                tempUser.getName(),
                tempUser.getMail(),
                tempUser.getPassword(),
                tempUser.getPermissionLevel(),
                tempList,
                tempUser.getSubscribedCategories());
        return true;
    }

    @Override
    public boolean unsubscribeCategory(int userId, int categoryId) throws UserNotFoundEx {
        System.out.println("unsubscribeCategory: " + userId + " " + categoryId);

        User tempUser = getUser(userId);
        List<Integer> tempList = tempUser.getSubscribedCategories();
        tempList.remove(categoryId);

        updateUser(userId,
                tempUser.getName(),
                tempUser.getMail(),
                tempUser.getPassword(),
                tempUser.getPermissionLevel(),
                tempUser.getSubscribedEvents(),
                tempList);
        return true;
    }

    @Override
    public int countUsersEvents(int id) throws UserNotFoundEx {
        System.out.println("countUsersEvents: " + id);
        User tempUser = getUser(id);
        List<Integer> tempList = tempUser.getSubscribedEvents();
        return tempList.size();
    }

    @Override
    public int countUsersCategories(int id) throws UserNotFoundEx {
        System.out.println("countUsersCategories: " + id);
        User tempUser = getUser(id);
        List<Integer> tempList = tempUser.getSubscribedCategories();
        return tempList.size();
    }

    @Override
    public List<Integer> getUsersEventList(int id) throws UserNotFoundEx {
        System.out.println("getUsersEventList: " + id);
        User tempUser = getUser(id);
        return tempUser.getSubscribedEvents();
    }

    @Override
    public List<Integer> getUsersCategoryList(int id) throws UserNotFoundEx {
        System.out.println("getUsersCategoryList: " + id);
        User tempUser = getUser(id);
        return tempUser.getSubscribedCategories();
    }

    @Override
    public boolean login(String nameOrMail, String password) throws UserNotFoundEx {
        System.out.println("login: " + nameOrMail + " " + password);
        User tempUser = getUser(nameOrMail);
        return password.equals(tempUser.getPassword());
    }

    @Override
    public boolean remindPassword(String nameOrMail) throws UserNotFoundEx {
        System.out.println("remindPassword");
        //todo jakieś wysyłanie maila z przypomnieniem
        return false;
    }

    @Override
    public boolean changePermissionLevel(int id, PermissionLevel permissionLevel) throws UserNotFoundEx {
        System.out.println("changePermissionLevel: " + id + " " + permissionLevel);
        User tempUser = getUser(id);

        updateUser(id,
                tempUser.getName(),
                tempUser.getMail(),
                tempUser.getPassword(),
                permissionLevel,
                tempUser.getSubscribedEvents(),
                tempUser.getSubscribedCategories());
        return true;
    }

}
