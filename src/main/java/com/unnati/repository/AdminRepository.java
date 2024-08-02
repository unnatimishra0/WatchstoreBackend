package com.unnati.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unnati.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer>{
	
	public Admin findByAdminEmailAndAdminPassword(String email,String Password);

}
