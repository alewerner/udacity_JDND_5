package com.example.demo;

import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.service.CartService;
import com.example.demo.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;

    private UserService userService = mock(UserService.class);
    private CartService cartService = mock(CartService.class);

    private User user;
    private Cart cart;
    private CreateUserRequest userRequest;

    @Before
    public void setup() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userService", userService);
        TestUtils.injectObjects(userController, "cartService", cartService);

        user = new User();
        user.setUsername("test");
        user.setPassword("password");

        cart = new Cart();

        userRequest = new CreateUserRequest();
        userRequest.setUsername("test");
        userRequest.setPassword("password");
        userRequest.setConfirmPassword("password");
    }

    @Test
    public void createUser_Success() throws Exception {

        when(userService.createUser(userRequest, user)).thenReturn(user);
        when(cartService.saveCart(userRequest)).thenReturn(cart);

        final ResponseEntity<User> response = userController.createUser(userRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        User user = response.getBody();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals("test", user.getUsername());
    }

    @Test
    public void createUser_fail() throws Exception {
        userRequest.setPassword("wrongpasswrd");

        final ResponseEntity<User> responseEntity = userController.createUser(userRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void findByUsername_success() {
        final String username = "findwithsuccess";
        user.setUsername(username);

        when(userService.findUser(username)).thenReturn(user);

        final ResponseEntity<User> responseEntity = userController.findByUserName(username);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user.getUsername(), responseEntity.getBody().getUsername());
    }

    @Test
    public void findByUsername_fail() {
        final String username = "willnotfind";
        user.setUsername(username);

        when(userService.findUser(username)).thenReturn(user);

        final ResponseEntity<User> responseEntity = userController.findByUserName("tryfindthis");

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void findById_Success() throws Exception {
        user.setId(1);
        user.setUsername("test");

        Optional<User> optionalUser = Optional.of(user);

        when(userService.findById(Long.valueOf(1))).thenReturn(optionalUser);

        final ResponseEntity<User> response = userController.findById(Long.valueOf(1));

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("test", response.getBody().getUsername());
    }

}
