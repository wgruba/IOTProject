package com.example.springboot.User;

import com.example.springboot.User.Exceptions.UserExistsEx;
import com.example.springboot.User.Exceptions.UserNotFoundEx;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    public UserRepositoryImpl(){}

    @Override
    public List<User> getAllUsers() {
        //todo podlinkować do funkcji Agaty: Users Read bez_filtra
        return null;
    }

    @Override
    public List<User> getUsersSubscribedToEvent(List<Integer> ids) {
        //todo podlinkować do funkcji Agaty: Users Read filtr ?
        return null;
    }

    @Override
    public User getUser(int id) throws UserNotFoundEx {
        //todo podlinkować do funkcji Agaty: Users Read filtr(id = id)
        return null;
    }

    @Override
    public User getUser(String nameOrMail) throws UserNotFoundEx {
        //todo podlinkować do funkcji Agaty: Users Read filtr(mail = nameOrMail || name = nameOrMail)
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
        //todo podlinkować do funkcji Agaty: Users Update filtr(id = id)
        return null;
    }

    @Override
    public boolean deleteUser(int id) throws UserNotFoundEx {
        //todo podlinkować do funkcji Agaty: Users Delete filtr(id = id)
        return true;
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
        //todo podlinkować do funkcji Agaty: Users Create
        return null;
    }

    @Override
    public User addUser(User user) throws UserExistsEx {
        //todo podlinkować do funkcji Agaty: Users Create
        return null;
    }

    @Override
    public int countUsers() {
        return getAllUsers().size();
    }

    @Override
    public PermissionLevel getPermissionLevel(int id) throws UserNotFoundEx {
        return getUser(id).getPermissionLevel();
    }

    @Override
    public boolean subscribeEvent(int userId, int eventId) throws UserNotFoundEx {
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
        User tempUser = getUser(id);
        List<Integer> tempList = tempUser.getSubscribedEvents();
        return tempList.size();
    }

    @Override
    public int countUsersCategories(int id) throws UserNotFoundEx {
        User tempUser = getUser(id);
        List<Integer> tempList = tempUser.getSubscribedCategories();
        return tempList.size();
    }

    @Override
    public List<Integer> getUsersEventList(int id) throws UserNotFoundEx {
        User tempUser = getUser(id);
        List<Integer> tempList = tempUser.getSubscribedEvents();
        return tempList;
    }

    @Override
    public List<Integer> getUsersCategoryList(int id) throws UserNotFoundEx {
        User tempUser = getUser(id);
        List<Integer> tempList = tempUser.getSubscribedCategories();
        return tempList;
    }

    @Override
    public boolean login(String nameOrMail, String password) throws UserNotFoundEx {
        User tempUser = getUser(nameOrMail);
        if(password.equals(tempUser))
            return true;
        return false;
    }

    @Override
    public boolean remindPassword(String nameOrMail) throws UserNotFoundEx {
        //todo jakieś wysyłanie maila z przypomnieniem
        return false;
    }

    @Override
    public boolean changePermissionLevel(int id, PermissionLevel permissionLevel) throws UserNotFoundEx {
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
