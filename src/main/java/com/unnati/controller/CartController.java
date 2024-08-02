package com.unnati.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unnati.dto.CartProductDto;
import com.unnati.dto.QuantityUpdateRequest;
import com.unnati.serviceImpl.CartServiceImpl;
import com.unnati.serviceImpl.ProductServiceImpl;

@RestController
@RequestMapping("/api")
public class CartController {

	@Autowired
	private CartServiceImpl cartServiceImpl;

	@Autowired
	private ProductServiceImpl productServiceImpl;

//	@PostMapping("/add")
//    public ResponseEntity<String> addItemToCart(@RequestBody AddToCartRequest request) {
//        boolean success = cartServiceImpl.addItemToCart(request.getProductId(), request.getQuantity());
//        if (success) {
//            return ResponseEntity.ok("Item added to cart successfully");
//        } else {
//            return ResponseEntity.badRequest().body("Failed to add item to cart");
//        }
//    }

	@PostMapping("/add/{userId}/{productId}")
	public ResponseEntity<String> addItemToCart(@PathVariable Long userId, @PathVariable Long productId) {
		String responseMessage = cartServiceImpl.addItemToCart(userId, productId);
		if (responseMessage.equals("Product added to cart successfully")) {
			return ResponseEntity.ok(responseMessage);

		} else if (responseMessage.equals("Product quantity updated in cart successfully")) {
			return ResponseEntity.ok(responseMessage);

		} else {
			return ResponseEntity.badRequest().body(responseMessage);
		}
	}

	@DeleteMapping("/delete/{userId}/{productId}")
	    public ResponseEntity<String> deleteItemFromCart(
	            @PathVariable Long userId,
	            @PathVariable Long productId) {
	        String result = cartServiceImpl.deleteItemFromCart(userId, productId);
	        if ("Product removed from cart successfully".equals(result)) {
	            return ResponseEntity.ok(result);
	        } else if ("Product removed from cart successfully".equals(result)) {
	        	return ResponseEntity.ok(result);
	        }
	        
	        else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
	        }
	    }

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<CartProductDto>> getAllProductsForUser(@PathVariable Long userId) {
		List<CartProductDto> cartProducts = cartServiceImpl.getAllProductsForUser(userId);
		return ResponseEntity.ok(cartProducts);
	}
	
	@DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable Long userId) {
        String response = cartServiceImpl.deleteAllItemsFromCart(userId);
        if ("All items deleted successfully.".equals(response)) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
	 @PutMapping("/increaseQuantity/{userId}/{productId}/{amount}")
	    public ResponseEntity<?> increaseQuantity(@PathVariable Long userId,
	                                               @PathVariable Long productId,
	                                               @PathVariable int amount) {
	        try {
	            boolean success = cartServiceImpl.increaseQuantity(userId, productId, amount);
	            if (success) {
	                return ResponseEntity.ok().build();
	            } else {
	                return ResponseEntity.status(400).body("Failed to increase quantity.");
	            }
	        } catch (Exception e) {
	            return ResponseEntity.status(500).body("Internal Server Error");
	        }
	    }

	    @PutMapping("/decreaseQuantity/{userId}/{productId}/{amount}")
	    public ResponseEntity<?> decreaseQuantity(@PathVariable Long userId,
	                                               @PathVariable Long productId,
	                                               @PathVariable int amount) {
	        try {
	            boolean success = cartServiceImpl.decreaseQuantity(userId, productId, amount);
	            if (success) {
	                return ResponseEntity.ok().build();
	            } else {
	                return ResponseEntity.status(400).body("Failed to decrease quantity.");
	            }
	        } catch (Exception e) {
	            return ResponseEntity.status(500).body("Internal Server Error");
	        }
	    }


}
