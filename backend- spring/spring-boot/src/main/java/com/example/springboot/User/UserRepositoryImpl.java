/*
package com.example.springboot.User;
import com.example.springboot.User.Exceptions.UserExistsEx;
import com.example.springboot.User.Exceptions.UserNotFoundEx;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private final String USERNAME = "Agata";
    private final String PASSWORD = "haslo";
    private final String DATABASE = "BazaDanych";

    @Autowired
    MongoTemplate mongoTemplate;

    public UserRepositoryImpl() {
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public List<User> getUsersSubscribedToEvent(List<Integer> ids) {
        return null;
    }

    @Override
    public User getUser(int id) throws UserNotFoundEx {
        return null;
    }

    @Override
    public User getUser(String nameOrMail) throws UserNotFoundEx {
        return null;
    }

    @Override
    public boolean updateUser(int id, String name, String mail, String password, PermissionLevel permissionLevel, List<Integer> subscribedEvents, List<Integer> subscribedCategories) throws UserNotFoundEx {
        return false;
    }

    @Override
    public boolean deleteUser(int id) throws UserNotFoundEx {
        return false;
    }

    @Override
    public boolean addUser(int id, String name, String mail, String password, PermissionLevel permissionLevel, List<Integer> subscribedEvents, List<Integer> subscribedCategories) throws UserExistsEx {
        return false;
    }

    @Override
    public boolean addUser(User user) throws UserExistsEx {
        return false;
    }

    @Override
    public int countUsers() {
        return 0;
    }

    @Override
    public PermissionLevel getPermissionLevel(int id) throws UserNotFoundEx {
        return null;
    }

    @Override
    public boolean subscribeEvent(int userId, int eventId) throws UserNotFoundEx {
        return false;
    }

    @Override
    public boolean subscribeCategory(int userId, int categoryId) throws UserNotFoundEx {
        return false;
    }

    @Override
    public boolean unsubscribeEvent(int userId, int eventId) throws UserNotFoundEx {
        return false;
    }

    @Override
    public boolean unsubscribeCategory(int userId, int categoryId) throws UserNotFoundEx {
        return false;
    }

    @Override
    public int countUsersEvents(int id) throws UserNotFoundEx {
        return 0;
    }

    @Override
    public int countUsersCategories(int id) throws UserNotFoundEx {
        return 0;
    }

    @Override
    public List<Integer> getUsersEventList(int id) throws UserNotFoundEx {
        return null;
    }

    @Override
    public List<Integer> getUsersCategoryList(int id) throws UserNotFoundEx {
        return null;
    }

    @Override
    public boolean login(String nameOrMail, String password) throws UserNotFoundEx {
        return false;
    }

    @Override
    public boolean remindPassword(String nameOrMail) throws UserNotFoundEx {
        return false;
    }

    @Override
    public boolean changePermissionLevel(int id, PermissionLevel permissionLevel) throws UserNotFoundEx {
        return false;
    }

@Override
    public List<User> getAllUsers() {
        System.out.println("getAllUsers");
        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();

        return dc.UserRead(df.allEntries());
    }

    @Override
    public List<User> getUsersSubscribedToEvent(List<Integer> ids) {
        System.out.println("getUsersSubscribedToEvent" + ids);
        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();

        return dc.UserRead(df.findAllIds(ids));
    }

    @Override
    public User getUser(int id) throws UserNotFoundEx {
        System.out.println("getUser" + id);
        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();

        return dc.UserRead(df.findById(id)).get(0);
    }

    @Override
    public User getUser(String nameOrMail) throws UserNotFoundEx {
        System.out.println("getUser" + nameOrMail);
        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();

        return dc.UserRead(df.checkUserExistence(nameOrMail)).get(0);
    }

    @Override
    public boolean updateUser(int id,
                              String name,
                              String mail,
                              String password,
                              PermissionLevel permissionLevel,
                              List<Integer> subscribedEvents,
                              List<Integer> subscribedCategories)
            throws UserNotFoundEx {
        System.out.println("updateUser: " + id + " " + name + " " + mail + " " + password + " " + permissionLevel + " " + subscribedEvents + " " + subscribedCategories);
        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        DatabaseUpdater du = new DatabaseUpdater();
        Bson update_fields = du.combineUpdates(
                du.updateName(name),
                du.updateEmail(mail),
                du.updatePassword(password),
                du.updatePermissionLevel(permissionLevel),
                du.updateUserEventSubscriptions(subscribedEvents),
                du.updateUserCategorySubscriptions(subscribedCategories)
        );
        return dc.UserCUD(new Bson[]{df.findById(id)}, new Bson[]{update_fields});
    }

    @Override
    public boolean deleteUser(int id) throws UserNotFoundEx {
        System.out.println("deleteUser: " + id);
        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();

        return dc.UserCUD(new Bson[]{df.findById(id)});
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

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        User user = new User(id, name, mail, password, permissionLevel, subscribedEvents, subscribedCategories);
        return dc.UserCUD(new User[]{user});
    }

    @Override
    public boolean addUser(User user) throws UserExistsEx {
        System.out.println("addUser: " + user);

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        return dc.UserCUD(new User[]{user});
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

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        DatabaseUpdater du = new DatabaseUpdater();

        return dc.UserCUD(new Bson[]{df.findById(userId)}, new Bson[]{du.subscribeUserToEvent(eventId)});
    }

    @Override
    public boolean subscribeCategory(int userId, int categoryId) throws UserNotFoundEx {
        System.out.println("subscribeCategory: " + userId + " " + categoryId);

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        DatabaseUpdater du = new DatabaseUpdater();

        return dc.UserCUD(new Bson[]{df.findById(userId)}, new Bson[]{du.subscribeUserToCategory(categoryId)});
    }

    @Override
    public boolean unsubscribeEvent(int userId, int eventId) throws UserNotFoundEx {
        System.out.println("unsubscribeEvent: " + userId + " " + eventId);


        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        DatabaseUpdater du = new DatabaseUpdater();

        return dc.UserCUD(new Bson[]{df.findById(userId)}, new Bson[]{du.unsubscribeUserFromEvent(eventId)});
    }

    @Override
    public boolean unsubscribeCategory(int userId, int categoryId) throws UserNotFoundEx {
        System.out.println("unsubscribeCategory: " + userId + " " + categoryId);

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        DatabaseUpdater du = new DatabaseUpdater();

        return dc.UserCUD(new Bson[]{df.findById(userId)}, new Bson[]{du.unsubscribeUserFromCategory(categoryId)});
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

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        DatabaseUpdater du = new DatabaseUpdater();

        return dc.UserCUD(new Bson[]{df.findById(id)}, new Bson[]{du.updatePermissionLevel(permissionLevel)});
    }


}
*/
