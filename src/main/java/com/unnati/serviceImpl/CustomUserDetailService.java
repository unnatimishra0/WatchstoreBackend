package com.unnati.serviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.unnati.entity.User;
import com.unnati.repository.UserDetailsRepository;

import lombok.Builder;



@Service
public class CustomUserDetailService {
	
//	@Autowired
//	private UserDetailsRepository userDetailsRepository;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		// TODO Auto-generated method stub
//		UserDetails user =userDetailsRepository.findByUsername(username);
//		
//		return user;
//		
//	}

}
