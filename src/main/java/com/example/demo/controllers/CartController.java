package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.service.CartService;
import com.example.demo.service.ItemService;
import com.example.demo.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private static final Logger log = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "Creates a cart from a valid ModifyCartRequest")
    @PostMapping("/addToCart")
    public ResponseEntity<Cart> addTocart(@RequestBody ModifyCartRequest request) {

        if (!checkItemAndUser(request.getItemId(), request.getUsername(), request)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Cart cart = cartService.saveCart(request);

        log.info("Success adding to cart");
        return ResponseEntity.ok(cart);
    }

    @ApiOperation(value = "Removes a item from a cart")
    @PostMapping("/removeFromCart")
    public ResponseEntity<Cart> removeFromcart(@RequestBody ModifyCartRequest request) {

        if (!checkItemAndUser(request.getItemId(), request.getUsername(), request)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("Success removing from cart");
        return ResponseEntity.ok(cartService.removeFromCart(request));
    }

    /**
     * Checks if the request has User and Item
     * @param itemId Long
     * @param userName String
     * @param request ModifyCartRequest
     * @return boolean */
    private boolean checkItemAndUser(Long itemId, String userName, ModifyCartRequest request) {

        User user = userService.findUser(userName);
        if (user == null) {
            log.error("Error with method. User '{}' not found", request.getUsername());
            return false;
        }

        Optional<Item> item = itemService.findItem(itemId);
        if (item == null) {
            log.error("Error with method. Item not found for id '{}'", request.getItemId());
            return false;
        }

		return true;
	}

}
