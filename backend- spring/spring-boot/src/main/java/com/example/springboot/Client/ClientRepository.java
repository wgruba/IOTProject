package com.example.springboot.Client;

import com.example.springboot.Client.Exceptions.ClientExistsEx;
import com.example.springboot.Client.Exceptions.ClientNotFoundEx;
import com.example.springboot.Event.Event;
import com.example.springboot.Category.Category;

import java.util.List;

public interface ClientRepository {
    List<Client> getAllClients();
    Client getClient(int id) throws ClientNotFoundEx;
    Client getClient(String nameOrMail) throws ClientNotFoundEx;

    Client updateClient(int id, String name, String mail, String password, PermissionLevel permissionLevel, List<Integer> subscribedEvents, List<Integer> subscribedCategories) throws ClientNotFoundEx;
    boolean deleteClient(int id) throws ClientNotFoundEx;
    Client addClient(int id, String name, String mail, String password, PermissionLevel permissionLevel, List<Integer> subscribedEvents, List<Integer> subscribedCategories) throws ClientExistsEx;
    Client addClient(Client client) throws ClientExistsEx;

    int countClients();
    PermissionLevel getPermissionLevel(int id) throws ClientNotFoundEx;

    //Subscription management
    boolean subscribeEvent(int id, Event event) throws ClientNotFoundEx;
    boolean subscribeCategory(int id, Category category) throws ClientNotFoundEx;
    boolean unsubscribeEvent(int id, Event event) throws ClientNotFoundEx;
    boolean unsubscribeCategory(int id, Category category) throws ClientNotFoundEx;

    //Client's saved events and categories
    int countClientsEvents(int id) throws ClientNotFoundEx;
    int countClientsCategories(int id) throws ClientNotFoundEx;
    List<Integer> showClientsEvents(int id) throws ClientNotFoundEx;
    List<Integer> showClientsCategories(int id) throws ClientNotFoundEx;

    //Account management
    boolean login(int id, String password);
    void remindPassword(String name, String mail);
    boolean changePermissionLevel(int id, PermissionLevel permissionLevel) throws ClientNotFoundEx;
}
