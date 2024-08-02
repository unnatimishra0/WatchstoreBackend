package com.unnati.services;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;

import com.unnati.entity.ProductDetails;
import com.unnati.entity.User;

public interface UserService {

	public User addUser(User user);

	public List<ProductDetails> searchWatches(String keyword);
//	 User getUserById(int userId);

	    User getUserInfo(String email);
	    
	    Page<User> getAllUsers(Pageable pageable);

//	    List<ProductDetails> getCart(List<Long> productIds);
	   // public List<ProductDetails> getCart();
	    
	    User getUserById(Long userId);

	    User updateUserInfo(String email, User user);
	   
	     User createUser(User user);
	     
	     List<User> getUsers();
}
