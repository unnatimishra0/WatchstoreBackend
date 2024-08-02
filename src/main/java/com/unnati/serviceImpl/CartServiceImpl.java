package com.unnati.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unnati.dto.CartProductDto;
import com.unnati.entity.CartUser;
import com.unnati.entity.ProductDetails;
import com.unnati.entity.User;
import com.unnati.repository.CartRepository;
import com.unnati.repository.ProductRepository;
import com.unnati.repository.UserDetailsRepository;
import com.unnati.services.CartService;

import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private UserDetailsRepository userRepo;

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private ProductRepository prodRepo;
	

	@Transactional
	public String addItemToCart(Long userId, Long productId) {
		// Fetch the user
		Optional<User> userOpt = userRepo.findById(userId);
		if (!userOpt.isPresent()) {
			return "User not found";
		}
		User user = userOpt.get();

		// Fetch the product
		Optional<ProductDetails> productOpt = prodRepo.findById(productId);
		if (!productOpt.isPresent()) {
			return "Product not found";
		}
		ProductDetails product = productOpt.get();

		// Check if the product is out of stock
		if (product.isOutOfStock()) {
			return "Product is out of stock";
		}

		// Check if the product already exists in the user's cart
		Optional<CartUser> cartUserOpt = cartRepo.findByUserAndWatch(user, product);
		if (cartUserOpt.isPresent()) {
			// Update the quantity if the product is already in the cart
			CartUser cartUser = cartUserOpt.get();
			cartUser.setQuantity(cartUser.getQuantity() + 1);
			cartRepo.save(cartUser);
			return "Product quantity updated in cart successfully";
		} else {
			// Create a new cart entry for the new product
			CartUser newCartUser = new CartUser();
			newCartUser.setUser(user);
			newCartUser.setWatch(product);
			newCartUser.setQuantity(1);
			cartRepo.save(newCartUser);
			return "Product added to cart successfully";
		}
	}

	@Transactional
    public String deleteItemFromCart(Long userId, Long productId) {
        // Find the item in the cart
        CartUser cartUser = cartRepo.findByUserIdAndProductId(userId, productId);

        if (cartUser != null) {
            // Remove the item from the cart
            cartRepo.deleteByUserIdAndProductId(userId, productId);
            return "Product removed from cart successfully";
        } else {
            return "Product not found in cart for the given user";
        }
    }
	
	  public String deleteAllItemsFromCart(Long userId) {
	        try {
	            cartRepo.deleteAllByUserId(userId);
	            return "All items deleted successfully.";
	        } catch (Exception e) {
	            // Handle exception if needed
	            return "Failed to delete items.";
	        }
	    }
	public List<CartProductDto> getAllProductsForUser(Long userId) {
	    // Find the user by ID
	    Optional<User> userOpt = userRepo.findById(userId);
	    
	    if (!userOpt.isPresent()) {
	        throw new RuntimeException("User not found");
	    }
	    
	    User user = userOpt.get();

	    List<CartUser> cartItems = cartRepo.findByUser(user);
	    
	    // Convert CartUser entities to CartProductDto
	    return cartItems.stream()
	            .map(cartItem -> new CartProductDto(
	                    cartItem.getWatch().getProductid(), // Map to DTO field
	                    cartItem.getWatch().getProductName(), // Map to DTO field
	                    cartItem.getWatch().getImage(), // Map to DTO field
	                    cartItem.getWatch().getDescription(), // Add description from ProductDetails
	                    cartItem.getWatch().getBrand(), // Map to DTO field
	                    cartItem.getWatch().getPrice(), // Map to DTO field
	                    cartItem.getQuantity() // Use quantity from CartUser
	            ))
	            .collect(Collectors.toList());
	}
	
	 public boolean increaseQuantity(Long userId, Long productId, int amount) {
	        return changeQuantity(userId, productId, amount);
	    }

	    public boolean decreaseQuantity(Long userId, Long productId, int amount) {
	        return changeQuantity(userId, productId, -amount);
	    }

	    private boolean changeQuantity(Long userId, Long productId, int amount) {
	        CartUser cartUser = cartRepo.findByUserIdAndProductId(userId, productId);
	        if (cartUser != null) {
	            int newQuantity = Math.max(cartUser.getQuantity() + amount, 0);

	            if (newQuantity == 0) {
	            	cartRepo.delete(cartUser);
	            } else {
	                cartUser.setQuantity(newQuantity);
	                cartRepo.save(cartUser);
	            }
	            return true;
	        }
	        return false;
	    }

	@Override
	public void addItem(Long productId, int quantity, Long user_id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteItem(Long userId, Long productId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllCartProducts(Long userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ProductDetails> getCartProducts(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}



}
