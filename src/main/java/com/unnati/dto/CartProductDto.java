package com.unnati.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder



public class CartProductDto {
	private Long Productid;
	private String productName;
	private String image;
	
	private String description;
	
	private String brand;
	private double price;
	
	private long quantity;
	

   
    // Getters and setters
}
