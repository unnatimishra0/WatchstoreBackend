package com.unnati.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unnati.entity.User;
import com.unnati.serviceImpl.UserServiceImpl;

@RestController
public class HomeTestController {
	
	@Autowired
	private UserServiceImpl userServiceImpl;

    Logger logger = LoggerFactory.getLogger(HomeTestController.class);


	@RequestMapping("/test")
    public String test() {
        this.logger.warn("This is working message");
        return "Testing message";
    }
	
	@GetMapping("/users")
	public List<User> getUser(){
		System.out.println("getting users");
		return userServiceImpl.getUsers();
		
	}
	


}