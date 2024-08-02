package com.unnati.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.unnati.entity.Order;
import com.unnati.entity.OrderItem;
import com.unnati.entity.ProductDetails;
import com.unnati.entity.User;

public interface OrderService {
	
	 Order getOrderById(Long orderId);

	    List<OrderItem> getOrderItemsByOrderId(Long orderId);
	    
	    Page<Order> getAllOrders(Pageable pageable);

	    Page<Order> getUserOrders(String email, Pageable pageable);

//	    Order postOrder(Order order, Map<Long, Integer> ProductId);

	    String deleteOrder(Long orderId);

//		Order postOrder(Order order, Long productId);
	    public Order postOrder(Long user_id, Long productId, int quantity);
//	    Order postOrder(int user_id, Long productId, int quantity);


//		Order postOrder(int user_id, Long productId);

//	    DataFetcher<List<Order>> getAllOrdersByQuery();
//
//	    DataFetcher<List<Order>> getUserOrdersByEmailQuery();

}
