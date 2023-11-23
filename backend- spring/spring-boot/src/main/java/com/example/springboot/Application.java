package com.example.springboot;

import com.example.springboot.Category.CategoryRepository;
import com.example.springboot.Event.EventRepository;
import com.example.springboot.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class Application {


	@Autowired
	UserRepository userRepository;

	@Autowired
	EventRepository eventRepository;
	@Autowired
	CategoryRepository categoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
