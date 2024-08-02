package com.unnati.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unnati.constants.OrderStatus;
import com.unnati.dto.OrderDto;
import com.unnati.entity.Order;
import com.unnati.serviceImpl.OrderServiceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
public class OrderController {

	@Autowired
	private OrderServiceImpl orderService;

//	 @PostMapping("/addorder")
//	    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
//	        Order order = orderService.postOrder(orderRequest.getOrder(), orderRequest.getProductId());
//	        return ResponseEntity.ok(order);
//	    }

	@PostMapping("/create/{userId}/{productId}/{quantity}")
    public ResponseEntity<?> createOrder(@PathVariable Long userId, @PathVariable Long productId, @PathVariable int quantity) {
        try {
            Order createdOrder = orderService.postOrder(userId, productId, quantity);
            if (createdOrder == null) {
                return ResponseEntity.badRequest().body("Failed to create order. Please check product availability or user details.");
            }
            return ResponseEntity.ok(createdOrder);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage()); // More specific error handling might be needed
        }
    }
	
	 @GetMapping("/user/{userId}")
	    public ResponseEntity<Page<OrderDto>> getUserOrders(
	            @PathVariable Long userId,
	            @PageableDefault(size = 10) Pageable pageable) {
	        Page<OrderDto> orders = orderService.getUserOrders(userId, pageable);
	        return ResponseEntity.ok(orders);
	    }
	@PutMapping("/{orderId}/status")
	public Order updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus status) {
		return orderService.updateOrderStatus(orderId, status);

	}
	
	  
	 @GetMapping("/track/{trackingId}")
	    public ResponseEntity<OrderDto> trackOrder(@PathVariable String trackingId) {
	        OrderDto orderDto = orderService. trackOrderByTrackingId(trackingId);
	        return ResponseEntity.ok(orderDto);
	    }
}
