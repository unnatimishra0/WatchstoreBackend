package com.unnati.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unnati.entity.Order;
import com.unnati.entity.OrderItem;

public interface OrderRepository extends JpaRepository<Order, Long> {

	void save(OrderItem orderItem);

	Page<Order> findByUserEmail(String email, Pageable pageable);

	List<OrderItem> findByOrderId(Long orderId);
	
	  @Query("SELECT o FROM Order o WHERE o.user.id = :userId")
	    Page<Order> findByUserId(@Param("userId") Long userId, Pageable pageable);
	  
//	    Order findByTrackingId(String trackingId);
	  
	    Optional<Order> findByTrackingId(String trackingId);




}
