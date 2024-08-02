package com.unnati.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unnati.entity.ProductDetails;
import com.unnati.entity.User;

public interface UserDetailsRepository extends JpaRepository<User, Long>{
	
	  Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	
	@Query("SELECT w FROM ProductDetails w WHERE " +
	           "LOWER(w.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "CAST(w.price AS string) LIKE CONCAT('%', :keyword, '%') OR " 
	           +
	           "LOWER(w.type) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	    List<ProductDetails> searchByKeyword(@Param("keyword") String keyword);

	Optional<User> findById(Long userId);

	boolean existsById(Long userId);

//    @Query("SELECT products FROM ProductDetails productDetails ORDER BY products.id ASC")
//	Page<User> findAllByOrderByIdAsc(Pageable pageable);

	boolean existsByEmail(String email);

}
