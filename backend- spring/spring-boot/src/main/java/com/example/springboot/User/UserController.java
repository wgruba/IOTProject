package com.example.springboot.User;

import com.example.springboot.User.Exceptions.UserNotFoundEx;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class UserController {
    private final UserRepository impl = new UserRepositoryImpl();

//    pokaż jakieś wydarzenia(na stronie głównej)
//		- Event R Random
//	- pokaż wydarzenie i jego szczegóły(na stronie konkretnego wydarzenia)
//		- Event R filtr
//	- przycisk logowania
//		- User R
//	- przycisk rejestracji
//		- User C
//	- wyszukiwanie wydarzeń(wyszukiwarka, filtry zależne od zaznaczonych rzeczy)
//		- Event R Filtrowane

    @GetMapping("/users/{id}")
    public EntityModel<User> getUser(@PathVariable int id){
        try {
            return EntityModel.of(impl.getUser(id));
        } catch (UserNotFoundEx e) {
            throw new RuntimeException(e);
        }
    }


}
