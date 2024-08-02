package com.unnati.model;

import java.util.Map;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.unnati.constants.ErrorMessages;
import com.unnati.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
	
	private Order order;
	
	 private Map<Long, Integer> productId;
	private Double totalPrice;
	    private Map<Long, Long> OrderId;

	    @NotBlank
	    private String firstName;

	    @NotBlank
	    private String lastName;

	    @NotBlank
	    private String city;

	    @NotBlank
	    private String address;

	    @Email
	    @NotBlank
	    private String email;

	    @NotBlank
	    private String phoneNumber;

}
