package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.service.CartService;
import com.example.demo.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Get a user by his Id")
    @GetMapping("/id/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity.of(userService.findById(id));
    }

    @ApiOperation(value = "Get a user by his username")
    @GetMapping("/{username}")
    public ResponseEntity<User> findByUserName(@PathVariable String username) {
        User user = userService.findUser(username);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Creates a user by a given CreateUserRequest")
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
        log.info("Creating user '{}'", createUserRequest.getUsername());

        if (createUserRequest.getPassword().length() < 7 ||
                !createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
            log.error("Error with user password. Cannot create user '{}'", createUserRequest.getUsername());
            return ResponseEntity.badRequest().build();
        }

        User user =  new User();
        user.setUsername(createUserRequest.getUsername());
        Cart cart = cartService.saveCart(createUserRequest);

        user.setCart(cart);
        userService.createUser(createUserRequest, user);

        log.info("Success user has been created for user '{}'", createUserRequest.getUsername());
        return ResponseEntity.ok(user);
    }

}
