package com.unnati.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unnati.entity.ProductDetails;
import com.unnati.entity.User;
import com.unnati.repository.ProductRepository;
import com.unnati.serviceImpl.UserServiceImpl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping
public class UserController {
	
	@Autowired
	private UserServiceImpl userService;
	
	
	@PostMapping("/registerUser")
	public ResponseEntity<?> addUser(@RequestBody User user){
		User userFromDb=userService.addUser(user);
		return new ResponseEntity<>(userFromDb,HttpStatus.OK);
		
	}
	
	
	@GetMapping("/searchBy/{key}")
	public ResponseEntity<?> searchByKeyword(@PathVariable String key){
		System.out.println(key);
		List<ProductDetails> searchList=userService.searchWatches(key);
		return new ResponseEntity<>(searchList,HttpStatus.OK);
		
	}
	
	

}
