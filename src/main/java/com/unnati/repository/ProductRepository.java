package com.unnati.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

import com.unnati.entity.Order;
import com.unnati.entity.ProductDetails;

public interface ProductRepository extends JpaRepository<ProductDetails, Long> {


//	Optional<ProductDetails> findById(ProductDetails productId);
    List<ProductDetails> findAllByOrderByLocalDateDesc();
    
    Optional<ProductDetails> findByModelNumber(String modelNumber);
    
    List<ProductDetails> findByOutOfStock(boolean outOfStock);





	

	
	

}
