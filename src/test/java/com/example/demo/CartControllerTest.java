package com.example.demo;

import com.example.demo.controllers.CartController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.service.CartService;
import com.example.demo.service.ItemService;
import com.example.demo.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@RunWith(SpringRunner.class)
//@DataJpaTest
//@Transactional
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CartControllerTest {

    private CartController cartController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private ItemRepository itemRepository = mock(ItemRepository.class);

    private UserService userService = mock(UserService.class);

    private ItemService itemService = mock(ItemService.class);

    private CartService cartService = mock(CartService.class);

    private ModifyCartRequest request;
    private Item item;
    private Optional<Item> optionalItem;
    private List<Item> items;
    private User user;
    private Cart cart;


    @Before
    public void setup() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userService", userService);
        TestUtils.injectObjects(cartController, "cartService", cartService);
        TestUtils.injectObjects(cartController, "itemService", itemService);

        request = new ModifyCartRequest();
        request.setItemId(1);
        request.setQuantity(1);
        request.setUsername("test");

        item = new Item();
        item.setDescription("Test item");
        item.setId(Long.valueOf(1));
        item.setName("Test item");
        item.setPrice(BigDecimal.valueOf(1234));

        optionalItem = Optional.of(item);

        items = new ArrayList<>();
        items.add(item);

        user = new User();
        user.setUsername("test");

        cart = new Cart();
        cart.setId(Long.valueOf(1));
        cart.setItems(items);
        cart.setUser(user);
        user.setCart(cart);
    }

    @Test
    public void addToCart_Success() throws Exception {
        when(userService.findUser("test")).thenReturn(user);
        when(itemService.findItem(Long.valueOf(1))).thenReturn(optionalItem);
        when(cartService.saveCart(request)).thenReturn(cart);

        final ResponseEntity<Cart> response = cartController.addTocart(request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Cart cartResponse = response.getBody();
        assertNotNull(cart);
        assertEquals(Long.valueOf(1), cartResponse.getId());
        assertEquals("test", cartResponse.getUser().getUsername());
    }

    @Test
    public void addToCart_Fail() throws Exception {
        when(userService.findUser("test")).thenReturn(null);

        final ResponseEntity<Cart> response = cartController.addTocart(request);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
