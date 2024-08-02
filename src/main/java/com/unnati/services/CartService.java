package com.unnati.services;

import java.util.List;

import com.unnati.entity.ProductDetails;

public interface CartService {

	public void addItem(Long productId,int quantity,Long user_id);

	public void deleteItem(Long userId, Long productId);

//	public void deleteAllCartProducts(Integer userId);

//	List<ProductDetails> getCartProducts(Integer userId);
	
	public void deleteAllCartProducts(Long userId);
	
	public List<ProductDetails> getCartProducts(Long userId);

}
