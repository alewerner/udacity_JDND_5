package com.example.demo.service;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    public Cart saveCart(ModifyCartRequest request) {

        User user = userService.findUser(request.getUsername());
        Optional<Item> item = itemService.findItem(request.getItemId());

        Cart cart = user.getCart();
        IntStream.range(0, request.getQuantity())
                .forEach(i -> cart.addItem(item.get()));
        cartRepository.save(cart);

        return cart;
    }

    public Cart saveCart(CreateUserRequest createUserRequest) {

        Cart cart = new Cart();
        User user = userService.findUser(createUserRequest.getUsername());
        cart.setUser(user);
        cartRepository.save(cart);

        return cart;
    }

    public Cart removeFromCart(ModifyCartRequest request) {

        User user = userService.findUser(request.getUsername());
        Optional<Item> item = itemService.findItem(request.getItemId());

        Cart cart = user.getCart();
        IntStream.range(0, request.getQuantity())
                .forEach(i -> cart.removeItem(item.get()));
        return cartRepository.save(cart);
    }
}
