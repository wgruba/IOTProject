package com.example.springboot.Client;

import com.example.springboot.Category.Category;
import com.example.springboot.Client.Exceptions.ClientExistsEx;
import com.example.springboot.Client.Exceptions.ClientNotFoundEx;
import com.example.springboot.Event.Event;

import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryImpl implements ClientRepository {
    private List<Client> clientList;
    public ClientRepositoryImpl(){
        clientList = new ArrayList<Client>();

        clientList.add(new Client(1, "client1", "example@example", "123", PermissionLevel.UNVERIFIED_USER, new ArrayList<Integer>(), new ArrayList<Integer>()));
        clientList.add(new Client(2, "client2", "example@example", "123", PermissionLevel.VERIFIED_USER, new ArrayList<Integer>(), new ArrayList<Integer>()));
        clientList.add(new Client(3, "client3", "example@example", "123", PermissionLevel.ADMIN, new ArrayList<Integer>(), new ArrayList<Integer>()));
        clientList.add(new Client(0, "client0", "example@example", "123", PermissionLevel.MODERATOR, new ArrayList<Integer>(), new ArrayList<Integer>()));
    }

    @Override
    public List<Client> getAllClients() {
        return clientList;
    }

    @Override
    public Client getClient(int id) throws ClientNotFoundEx {
        for(Client theClient:clientList){
            if(theClient.getId() == id){
                return theClient;
            }
        }
        throw new ClientNotFoundEx(id);
    }

    @Override
    public Client getClient(String nameOrMail) throws ClientNotFoundEx {
        for(Client theClient:clientList){
            if(theClient.getName().equals(nameOrMail) || theClient.getMail().equals(nameOrMail)){
                return theClient;
            }
        }
        throw new ClientNotFoundEx(nameOrMail);
    }

    @Override
    public Client updateClient(int id,
                               String name,
                               String mail,
                               String password,
                               PermissionLevel permissionLevel,
                               List<Integer> subscribedEvents,
                               List<Integer> subscribedCategories)
            throws ClientNotFoundEx {
        for(Client theClient:clientList){
            if(theClient.getId() == id){
                theClient.setName(name);
                theClient.setMail(mail);
                theClient.setPassword(password);
                theClient.setPermissionLevel(permissionLevel);
                theClient.setSubscribedEvents(subscribedEvents);
                theClient.setSubscribedCategories(subscribedCategories);
            }
        }
        throw new ClientNotFoundEx(id);
    }

    @Override
    public boolean deleteClient(int id) throws ClientNotFoundEx {
        for(Client theClient:clientList){
            if(theClient.getId() == id){
                clientList.remove(theClient);
                return true;
            }
        }
        throw new ClientNotFoundEx(id);
    }

    @Override
    public Client addClient(int id,
                            String name,
                            String mail,
                            String password,
                            PermissionLevel permissionLevel,
                            List<Integer> subscribedEvents,
                            List<Integer> subscribedCategories)
            throws ClientExistsEx {
        for(Client theClient:clientList){
            if(theClient.getId() == id){
                throw new ClientExistsEx(id);
            }
        }
        Client client = new Client(id, name, mail, password, permissionLevel, subscribedEvents, subscribedCategories);
        clientList.add(client);
        return client;
    }

    @Override
    public Client addClient(Client client) throws ClientExistsEx {
        int id = client.getId();
        for(Client theClient:clientList){
            if(theClient.getId() == id){
                throw new ClientExistsEx(id);
            }
        }
        clientList.add(client);
        return client;
    }

    @Override
    public int countClients() {
        return clientList.size();
    }

    @Override
    public PermissionLevel getPermissionLevel(int id) throws ClientNotFoundEx {
        for(Client theClient:clientList){
            if(theClient.getId() == id){
                return theClient.getPermissionLevel();
            }
        }
        throw new ClientNotFoundEx(id);
    }

    @Override
    public boolean subscribeEvent(int id, Event event) throws ClientNotFoundEx {
        for(Client theClient:clientList){
            if(theClient.getId() == id){
                List<Integer> subscribedEvents = theClient.getSubscribedEvents();
                subscribedEvents.add(event.getId());
                theClient.setSubscribedEvents(subscribedEvents);
                return true;
            }
        }
        throw new ClientNotFoundEx(id);
    }

    @Override
    public boolean subscribeCategory(int id, Category category) throws ClientNotFoundEx {
        for(Client theClient:clientList){
            if(theClient.getId() == id){
                List<Integer> subscribedCategories = theClient.getSubscribedCategories();
                subscribedCategories.add(category.getId());
                theClient.setSubscribedCategories(subscribedCategories);
                return true;
            }
        }
        throw new ClientNotFoundEx(id);
    }

    @Override
    public boolean unsubscribeEvent(int id, Event event) throws ClientNotFoundEx {
        for(Client theClient:clientList){
            if(theClient.getId() == id){
                List<Integer> subscribedEvents = theClient.getSubscribedEvents();
                subscribedEvents.remove(event.getId());
                theClient.setSubscribedEvents(subscribedEvents);
                return true;
            }
        }
        throw new ClientNotFoundEx(id);
    }

    @Override
    public boolean unsubscribeCategory(int id, Category category) throws ClientNotFoundEx {
        for(Client theClient:clientList){
            if(theClient.getId() == id){
                List<Integer> subscribedCategories = theClient.getSubscribedCategories();
                subscribedCategories.remove(category.getId());
                theClient.setSubscribedCategories(subscribedCategories);
                return true;
            }
        }
        throw new ClientNotFoundEx(id);
    }

    @Override
    public int countClientsEvents(int id) throws ClientNotFoundEx {
        for(Client theClient:clientList){
            if(theClient.getId() == id){
                return theClient.getSubscribedEvents().size();
            }
        }
        throw new ClientNotFoundEx(id);
    }

    @Override
    public int countClientsCategories(int id) throws ClientNotFoundEx {
        for(Client theClient:clientList){
            if(theClient.getId() == id){
                return theClient.getSubscribedCategories().size();
            }
        }
        throw new ClientNotFoundEx(id);
    }

    @Override
    public List<Integer> showClientsEvents(int id) throws ClientNotFoundEx {
        for(Client theClient:clientList){
            if(theClient.getId() == id){
                return theClient.getSubscribedEvents();
            }
        }
        throw new ClientNotFoundEx(id);
    }

    @Override
    public List<Integer> showClientsCategories(int id) throws ClientNotFoundEx {
        for(Client theClient:clientList){
            if(theClient.getId() == id){
                return theClient.getSubscribedCategories();
            }
        }
        throw new ClientNotFoundEx(id);
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
    public boolean changePermissionLevel(int id, PermissionLevel permissionLevel) throws ClientNotFoundEx {
        for(Client theClient:clientList){
            if(theClient.getId() == id){
                theClient.setPermissionLevel(permissionLevel);
                return true;
            }
        }
        throw new ClientNotFoundEx(id);
    }

}
