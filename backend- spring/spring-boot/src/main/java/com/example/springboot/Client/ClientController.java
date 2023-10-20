package com.example.springboot.Client;

import com.example.springboot.Client.Client;
import com.example.springboot.Client.Exceptions.ClientNotFoundEx;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class ClientController {
    //todo rest api for clients
    private final ClientRepository impl = new ClientRepositoryImpl();
    
    @GetMapping("/clients/{id}")
    public EntityModel<Client> getClient(@PathVariable int id){
        try {
            return EntityModel.of(impl.getClient(id));
        } catch (ClientNotFoundEx e) {
            throw new RuntimeException(e);
        }
    }
//    public EntityModel<?> getClient(@PathVariable int id) {
//        try{
//            return EntityModel.of(impl.getClient(id),
//                    linkTo(methodOn(ClientController.class).getClient(id)).withSelfRel(),
//                    linkTo(methodOn(ClientController.class).deleteClient(id)).withRel("delete"),
//                    linkTo(methodOn(ClientController.class).getClient()).withRel("list all")
//            );
//        } catch (ClientNotFoundEx e) {
//            System.out.println("...GET Exception");
//            throw new RuntimeException(e);
//        }
//    }

}
