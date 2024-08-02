package com.unnati.serviceImpl;

import static com.unnati.constants.ErrorMessages.EMAIL_NOT_FOUND;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.unnati.entity.CartUser;
import com.unnati.entity.ProductDetails;
import com.unnati.entity.User;
import com.unnati.exception.*;
import com.unnati.model.*;
import com.unnati.repository.CartRepository;
import com.unnati.repository.UserDetailsRepository;
import com.unnati.services.CartService;
import com.unnati.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDetailsRepository userRepo;

	@Autowired
	private CartRepository cartRepo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

	

	public User addUser(User user) {
		Optional<User> existingUser = userRepo.findByEmail(user.getEmail());
		if (existingUser.isPresent()) {
			throw new IllegalArgumentException("User already exists with this email");
		}
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		CartUser cartUser=new CartUser();
		cartUser.setUser(user);
		cartRepo.save(cartUser);
		return userRepo.save(user);
	}

	@Override

	public List<ProductDetails> searchWatches(String keyword) {
		List<ProductDetails> searchList = userRepo.searchByKeyword(keyword);

		return searchList;
	}

	@Override
	public User getUserById(Long userId) {
		// TODO Auto-generated method stub
		if (userRepo.existsById(userId)) {
			userRepo.findById(userId);
		} else {
			System.out.println("USer does not exists");
		}
		return null;
	}

	@Override
	public User getUserInfo(String email) {
		// TODO Auto-generated method stub
		if (userRepo.existsByEmail(email)) {
			userRepo.findByEmail(email);
		}
		return null;
	}

	@Override
	public Page<User> getAllUsers(Pageable pageable) {
		// TODO Auto-generated method stub
//        return userRepo.findAllByOrderByIdAsc(pageable);
		return null;

	}

	@Override
	public User updateUserInfo(String email, User user) {
		// TODO Auto-generated method stub
		User userDb = userRepo.findByEmail(email)
				.orElseThrow(() -> new ApiRequestException(EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));
		userDb.setName(user.getName());
		userDb.setAddress(user.getAddress());
		userDb.setEmail(user.getEmail());

		return userDb;
	}

//	@Override
//	public List<ProductDetails> getCart(List<Long> productIds) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
//	public List<ProductDetails> getCart() {
//        String username = getUsername();
//        logger.info("Extracted email: {}", username);
//
//        Optional<User> user = userRepo.findByUsername(username);
//        if (user.isPresent()) {
//            List<CartUser> carts = cartRepo.findByUser_Id(user.get().getUser_id());
//            logger.info("Fetched carts for user: {}", carts);
//            return carts.stream().map(CartUser::getProduct).collect(Collectors.toList());
//        } else {
//            logger.error("User not found with email: {}", username);
//            throw new UsernameNotFoundException("User not found with email: " + username);
//        }
//    }

    private String getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            return jwtService.extractUsername((String) principal);
        } else {
            throw new IllegalStateException("Invalid principal type");
        }
    }

//	private String getEmail() {
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		if (principal instanceof UserDetails) {
//			return ((UserDetails) principal).getUsername();
//		} else {
//			return principal.toString();
//		}
//	}

	@Override
	public List<User> getUsers() {
		return userRepo.findAll();

	}

	@Override
	public User createUser(User user) {
		user.setUser_id(123L);
		return userRepo.save(user);

	}

	
}
