package com.unnati.services;

import java.util.List;

import com.unnati.entity.ProductDetails;

public interface ProductService {

	
	public void addProduct(ProductDetails productDetails);
	
	public void deleteProduct(Long productId);
	
	public ProductDetails getProduct(Long productId);
	
	public List<ProductDetails> getAllProduct();
	
	public ProductDetails updateProduct(Long productId ,ProductDetails productDetails);
}
