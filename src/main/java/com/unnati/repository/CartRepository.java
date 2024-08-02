package com.unnati.repository;

import java.util.List;
import java.util.Optional;
import java.lang.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unnati.entity.CartUser;
import com.unnati.entity.ProductDetails;
import com.unnati.entity.User;

import jakarta.transaction.Transactional;

public interface CartRepository extends JpaRepository<CartUser, Long>{


//	List<CartUser> findByUserId(Integer userId);

//    Optional<CartUser> findByUserAndProduct(User user, ProductDetails product);

	List<CartUser> findById(Optional<User> user);
	
	
    List<CartUser> findByUser(User user);
    
    
    @Query("SELECT c FROM CartUser c WHERE c.user.id = :userId")
    List<CartUser> findByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartUser c WHERE c.user.id = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);

    Optional<CartUser> findByUserAndWatch(User user, ProductDetails product);

    
    
    @Query("SELECT c FROM CartUser c WHERE c.user.id = :userId AND c.watch.id = :productId")
    CartUser findByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartUser c WHERE c.user.id = :userId AND c.watch.id = :productId")
    void deleteByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);


//	Optional<ProductDetails> findByUserIdAndProductId(Long userId, Long productId);



//    List<CartUser> findByUserAndWatch(User user, ProductDetails watch);



//	@Query("SELECT c FROM CartUser c WHERE c.user.user_id = ?1")
//    List<CartUser> findByUser_Id(Long long1);
//	}

//	 List<CartUser> findByUser_Id(int userId);
	
	

}