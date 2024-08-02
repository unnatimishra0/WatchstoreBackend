package com.unnati.dto;

import java.sql.Date;

import com.unnati.constants.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
	
	 private Long orderId;
	    private Long user_id;
		private Long Productid;
	    private Date orderDate;
	    private OrderStatus status;
	    private int quantity;
	    private String trackingId; // Include the tracking ID


}
