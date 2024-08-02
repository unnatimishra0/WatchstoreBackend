
package com.unnati.controller;

import java.util.List;

import org.apache.commons.mail.EmailException;
import org.hibernate.bytecode.internal.bytebuddy.PrivateAccessorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unnati.entity.ProductDetails;
import com.unnati.serviceImpl.EmailService;
import com.unnati.serviceImpl.ProductServiceImpl;
import com.unnati.services.ProductService;

@RestController
@RequestMapping
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductServiceImpl productServiceImpl;

	@Autowired
	private EmailService emailService;

	@PostMapping("/addProd")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> addProduct(@RequestBody ProductDetails productDetails) {
		productService.addProduct(productDetails);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@DeleteMapping("/deleteProd/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/getProd/{id}")
	public ResponseEntity<?> getProduct(@PathVariable Long id) {
		ProductDetails product = productService.getProduct(id);
		if (product != null) {
			System.out.println(product + "jiiiii");
			return new ResponseEntity<>(product, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}
	}
	
	@GetMapping("/pieChart")
	public int[] pieChart() {
		return productServiceImpl.inStockIsToTotal();
	}

	@GetMapping("/getAllProducts")
	public List<ProductDetails> getProducts() {
		List<ProductDetails> userProducts = productService.getAllProduct();
		return userProducts;
	}

	@GetMapping("/model/{modelNumber}")
	public ProductDetails getWatchByModelNumber(@PathVariable String modelNumber) {
		return productServiceImpl.getWatchByModelNumber(modelNumber);
	}

	@GetMapping("/sorted-by-latest")
	public List<ProductDetails> getWatchesSortedByLatestArrival() {
		return productServiceImpl.getWatchesSortedByLatestArrival();
	}

	@PostMapping("/{id}/out-of-stock")
	public void markWatchAsOutOfStock(@PathVariable Long id) {
		productServiceImpl.markWatchAsOutOfStock(id);
	}

	@PostMapping("/{id}/update-stock")
	public void updateWatchStockBasedOnOrders(@PathVariable Long id) {
		productServiceImpl.updateWatchStockBasedOnOrders(id);
	}

	@PostMapping("/{productId}/interest")
	public ResponseEntity<?> showInterest(@PathVariable Long productId, @RequestParam String userEmail) {
		try {
			// Assuming a method exists to get product details by ID
			ProductDetails product = productServiceImpl.getProductById(productId);

			if (product.isOutOfStock()) {
				String subject = "Interest in Out-of-Stock Product: " + product.getProductName();
				String message = "User " + userEmail + " has shown interest in the out-of-stock product: "
						+ product.getProductName();
				emailService.sendEmail("unnatimishra19@gmail.com", subject, message);

				return ResponseEntity.ok("Your interest has been noted. Admin will be notified.");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product is not out of stock.");
			}
		} catch (MailException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send notification email.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
		}
	}

}
