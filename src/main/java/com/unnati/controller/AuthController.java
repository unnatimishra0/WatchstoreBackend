package com.unnati.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unnati.entity.ProductDetails;
import com.unnati.entity.User;
import com.unnati.model.AuthRequest;
import com.unnati.repository.UserDetailsRepository;
import com.unnati.serviceImpl.CartServiceImpl;
import com.unnati.serviceImpl.JwtService;
import com.unnati.serviceImpl.UserServiceImpl;

@RestController
@RequestMapping("/products")
public class AuthController {

    @Autowired
    private UserServiceImpl service;
    
    @Autowired
    private CartServiceImpl cartService;
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private UserDetailsRepository userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/new")
    public User addUser(@RequestBody User userInfo) {
        return service.addUser(userInfo);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> getAllUsers() {
        return service.getUsers();
    }

    @GetMapping("/cart/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<ProductDetails> getCart(@PathVariable Long id) {
    	System.err.println("service called");
        return cartService.getCartProducts(id);
        
    }


//    @PostMapping("/authenticate")
//    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//        if (authentication.isAuthenticated()) {
//            return jwtService.generateToken(authRequest.getUsername());
//        } else {
//            throw new UsernameNotFoundException("invalid user request !");
//        }
//
//
//    }
    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
    	
        Optional<User> user = userRepo.findByUsername(authRequest.getUsername());
        if (user.isPresent()) {
            System.out.println(user.get().getRoles());
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {

            // Get user role (assuming User entity has a 'roles' field)
            String role = user.map(User::getRoles).orElse("USER");
            
            Long userId=user.map(User::getUser_id).orElse(null);
            String token = jwtService.generateToken(authRequest.getUsername(),userId,role);
            

            // Prepare response map
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
//            response.put("role", role);
//            response.put("userId", userId+"");

            System.out.println("Generated Token: " + token);

            // Return response with token and role
            return ResponseEntity.ok(response);
        } else {
            throw new UsernameNotFoundException("Invalid user credentials!");
        }
    }

}