package com.unnati.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unnati.entity.ProductDetails;
import com.unnati.repository.ProductRepository;
import com.unnati.services.ProductService;

import io.jsonwebtoken.lang.Collections;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public void addProduct(ProductDetails productDetails) {
		// TODO Auto-generated method stub
	    System.out.println("Adding product with model number: " + productDetails.getModelNumber());

		productRepository.save(productDetails);

	}
	public ProductDetails getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
    }

	@Override
	public void deleteProduct(Long productId) {
		// TODO Auto-generated method stub
		productRepository.deleteById(productId);

	}

	@Override
	public ProductDetails getProduct(Long productId) {
		// TODO Auto-generated method stub
		Optional<ProductDetails> getProd = productRepository.findById(productId);
		System.out.println("getProd founnnnnnd"+getProd);
		if (getProd.isPresent()) {
			System.out.println("getProd found"+getProd);

			return getProd.get();
		}
		else {
			System.out.println("Product with given id not found");
		}
		return null;
	}

	@Override
	public List<ProductDetails> getAllProduct() {
		// TODO Auto-generated method stub
		List<ProductDetails> p1=productRepository.findAll();
		return p1;
	}
	
	public ProductDetails getWatchByModelNumber(String modelNumber) {
        return productRepository.findByModelNumber(modelNumber)
                .orElseThrow(() -> new RuntimeException("Watch not found with model number: " + modelNumber));
    }
	
	public List<ProductDetails> getWatchesSortedByLatestArrival() {
        return productRepository.findAllByOrderByLocalDateDesc();
    }

	@Override
	public ProductDetails updateProduct(Long productId, ProductDetails productDetails) {
		// TODO Auto-generated method stub
		Optional<ProductDetails> optUser=productRepository.findById(productId);
		
		ProductDetails product=optUser.get();
		if(product!=null) {
			product.setProductName(productDetails.getProductName());
			product.setDescription(productDetails.getDescription());
			product.setImage(productDetails.getImage());
			product.setPrice(productDetails.getPrice());
			product.setProductid(productDetails.getProductid());
		}
		productRepository.save(product);
		return product==null ? null:productRepository.findById(productId).get();
	}
	
//	@Transactional
    public void markWatchAsOutOfStock(Long watchId) {
        ProductDetails watch = productRepository.findById(watchId).orElseThrow(() -> new RuntimeException("Watch not found"));
        watch.setOutOfStock(true);
        productRepository.save(watch);
    }

    public void updateWatchStockBasedOnOrders(Long watchId) {
    	ProductDetails watch = productRepository.findById(watchId).orElseThrow(() -> new RuntimeException("Watch not found"));
        
        boolean isOutOfStock = !isAvailableInStock(watchId);
        watch.setOutOfStock(isOutOfStock);
        productRepository.save(watch);
    }

    private boolean isAvailableInStock(Long watchId) {
    	ProductDetails watch = productRepository.findById(watchId).orElseThrow(() -> new RuntimeException("Watch not found"));
        
        return watch.getQuantity() > 0;
    }
    
    public int[] inStockIsToTotal() {
    	int[] arr=new int[2];
    	List<ProductDetails> listInStock=productRepository.findByOutOfStock(false);
    	List<ProductDetails> listTotal=productRepository.findAll();
		int countTotal=Collections.size(listTotal);
		int countInStock=Collections.size(listInStock);
		arr[0]=countInStock;
		arr[1]=countTotal;
		
		
    	return arr;
    	
    	
    }
	

}
