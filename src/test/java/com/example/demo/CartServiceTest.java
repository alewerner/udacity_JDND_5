package com.example.demo;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.service.CartService;
import com.example.demo.service.ItemService;
import com.example.demo.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@RunWith(SpringRunner.class)
public class CartServiceTest {

    /*private CartService cartService;

    private UserService userService = mock(UserService.class);

    private ItemService itemService = mock(ItemService.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private User user;
    private Item item;
    private Optional<Item> listItens;
    private Cart cart;
    private ModifyCartRequest request;

    @Before
    public void Setup() {
        user = new User();
        user.setPassword("1234567");
        user.setUsername("userTest");

        item = new Item();
        item.setDescription("item test");
        item.setName("item test");
        BigDecimal price = BigDecimal.valueOf(1000.0);
        item.setPrice(price);

        List<Item> listItens = new ArrayList<>();
        listItens.add(item);

        cart = new Cart();
        cart.setUser(user);
        cart.setItems(listItens);
        cart.setTotal(BigDecimal.valueOf(1000.0));

        request = new ModifyCartRequest();
        request.setItemId(1);
        request.setQuantity(1);
        request.setUsername("test");
    }


    @Test
    public void saveCart() {

        when(userService.findUser("test")).thenReturn(user);
        when(itemService.findItem(Long.valueOf(1))).thenReturn(listItens);
        when(cartRepository.save(any())).thenReturn(cart);
        //when(user.getCart()).willReturn(cart);

        cartService.saveCart(request);

        verify(cartRepository.save(any()), times(1));
        //verify(eq(1), cart.getItems().size());
    }*/
}
