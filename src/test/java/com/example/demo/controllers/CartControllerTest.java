package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CartControllerTest {
    private static CartController cartController;

    private static UserRepository userRepository = mock(UserRepository.class);
    private static CartRepository cartRepository = mock(CartRepository.class);
    private static ItemRepository itemRepository = mock(ItemRepository.class);

    @BeforeAll
    public static void setUp() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
    }


    @Test
    public void addTocart() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setCart(new Cart());
        when(userRepository.findByUsername("user")).thenReturn(user);

        Item item = new Item();
        item.setId(1L);
        item.setName("item");
        item.setPrice(new BigDecimal(10));
        item.setDescription("description");
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("user");
        request.setItemId(1L);
        request.setQuantity(10);

        ResponseEntity<Cart> response = cartController.addTocart(request);
        assertNotNull(response);

        Cart cart = response.getBody();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(cart.getItems().size(), 10);
        assertEquals(cart.getTotal(), new BigDecimal(100));
    }

    @Test
    public void removeFromcart() {

        Item item = new Item();
        item.setId(1L);
        item.setName("item");
        item.setPrice(new BigDecimal(10));
        item.setDescription("description");
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        //add items to list
        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item);
        items.add(item);
        items.add(item);

        Cart cart = new Cart();
        cart.setItems(items);

        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setCart(cart);
        when(userRepository.findByUsername("user")).thenReturn(user);


        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(1L);
        request.setQuantity(2);
        request.setUsername("user");

        ResponseEntity<Cart> response = cartController.removeFromcart(request);
        assertNotNull(response);

        Cart cartFromResponse = response.getBody();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, cartFromResponse.getItems().size());

    }
}
