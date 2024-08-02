package com.unnati.model;

import java.time.Instant;
import java.time.LocalDate;

import com.unnati.entity.ProductDetails;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
	
	 private Long id;
	    private Double totalPrice;
	    private LocalDate date;
	    private String firstName;
	    private String lastName;
	    private String city;
	    private String address;
	    private String email;
	    private String phoneNumber;
}
