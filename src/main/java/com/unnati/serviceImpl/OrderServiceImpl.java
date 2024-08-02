package com.unnati.serviceImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.unnati.constants.OrderStatus;
import com.unnati.dto.OrderDto;
import com.unnati.entity.Order;
import com.unnati.entity.OrderItem;
import com.unnati.entity.ProductDetails;
import com.unnati.entity.User;
import com.unnati.repository.OrderRepository;
import com.unnati.repository.ProductRepository;
import com.unnati.repository.UserDetailsRepository;
import com.unnati.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private UserDetailsRepository userRepo;

	public Order getOrderById(Long orderId) {
        Optional<Order> order = orderRepo.findById(orderId);
        return order.orElse(null);  // return null if the order is not found, or throw an exception if preferred
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderRepo.findByOrderId(orderId);
    }

	

	 public Page<OrderDto> getUserOrders(Long userId, Pageable pageable) {
	        Page<Order> orders = orderRepo.findByUserId(userId, pageable);
	        List<OrderDto> orderDtos = orders.getContent().stream()
	                .map(this::convertToDto)
	                .collect(Collectors.toList());
	        return new PageImpl<>(orderDtos, pageable, orders.getTotalElements());
	    }

	    private OrderDto convertToDto(Order order) {
	        return new OrderDto(
	                order.getOrderId(),
	                order.getUser().getUser_id(), 
	                order.getProduct().getProductid(), 
	                order.getOrderDate(),
	                order.getStatus(),
	                order.getQuantity(),
	                order.getTrackingId()
	        );
	    }
	@Override
	public String deleteOrder(Long orderId) {
		// TODO Auto-generated method stub
		if (orderRepo.existsById(orderId)) {
			orderRepo.deleteById(orderId);
			return "Order with ID " + orderId + " has been deleted successfully.";
		}
		else {
            return "Order with ID " + orderId + " does not exist.";

		}
	}

	@Override
	public Order postOrder(Long user_id, Long productId, int quantity) {
	    Optional<ProductDetails> product = productRepo.findById(productId);
	    Optional<User> user = userRepo.findById(user_id);

	    // Check if both product and user exist
	    if (product.isEmpty() || user.isEmpty()) {
	        if (product.isEmpty()) {
	            System.out.println("Product is not available");
	        }
	        if (user.isEmpty()) {
	            System.out.println("User is not found");
	        }
	        return null;
	    }

	    // Check if product has sufficient quantity
	    if (product.get().getQuantity() < quantity) {
	        System.out.println("Product is out of stock. Only " + product.get().getQuantity() + " available.");
	        return null; // Order cannot be placed due to insufficient stock
	    }

	    ProductDetails updatedProduct = product.get(); // Get the product object

	    // Update product quantity (assuming `ProductDetails` has a `setQuantity` method)
	    updatedProduct.setQuantity(updatedProduct.getQuantity() - quantity);

	    // Save the updated product quantity (assuming `productRepo` has a `save` method for ProductDetails)
	    productRepo.save(updatedProduct);

	    // Build and save the order with quantity information
	    Order order = Order.builder()
	            .orderDate(Date.valueOf(LocalDate.now()))
	            .user(user.get())
	            .product(product.get())
	            .quantity(quantity) // Add quantity to the order
	            .status(OrderStatus.ORDER_PLACED)
	            .build();

	    return orderRepo.save(order);
	}
	
	 public OrderDto trackOrderByTrackingId(String trackingId) {
	        Optional<Order> orderOptional = orderRepo.findByTrackingId(trackingId);
	        if (orderOptional.isPresent()) {
	            Order order = orderOptional.get();
	            return convertToDto2(order);
	        } else {
	            throw new RuntimeException("Order not found with tracking ID: " + trackingId);
	        }
	    }

	    private OrderDto convertToDto2(Order order) {
	        return new OrderDto(
	            order.getOrderId(),
	            order.getUser().getUser_id(),  // Assuming your User entity has a getId() method
	            order.getProduct().getProductid(),  // Assuming your ProductDetails entity has a getProductId() method
	            order.getOrderDate(),
	            order.getStatus(),
	            order.getQuantity(),
	            order.getTrackingId()
	        );
	    }
	
	public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        order.setStatus(status);
        return orderRepo.save(order);
    }

	@Override
	public Page<Order> getUserOrders(String email, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> getAllOrders(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
