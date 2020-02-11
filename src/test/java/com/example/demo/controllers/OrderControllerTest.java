package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private static OrderController orderController;

    private static UserRepository userRepository = mock(UserRepository.class);

    private static OrderRepository orderRepository = mock(OrderRepository.class);

    @BeforeAll
    public static void setUp() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);

    }

    @Test
    public void submit() {
        Item item = new Item();
        item.setId(1L);
        item.setName("test item");
        item.setPrice(new BigDecimal("1"));
        item.setDescription("test item");

        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item);
        items.add(item);

        Cart cart = new Cart();
        cart.setItems(items);

        User user = new User();
        user.setId(20L);
        user.setUsername("user");
        user.setCart(cart);

        when(userRepository.findByUsername("user")).thenReturn(user);
        ResponseEntity<UserOrder> response = orderController.submit("user");
        assertNotNull(response);
        UserOrder userOrder = response.getBody();
        assertNotNull(userOrder);
        assertEquals(cart.getTotal(), userOrder.getTotal());
        assertEquals(200, response.getStatusCodeValue());

    }

    @Test
    public void getOrdersForUser() {
        Item item = new Item();
        item.setId(1L);
        item.setName("item");
        item.setPrice(new BigDecimal(1));
        item.setDescription("description");

        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        UserOrder order = new UserOrder();
        order.setId(1L);
        order.setItems(Arrays.asList(item, item,item));
        order.setUser(user);
        order.setTotal(new BigDecimal(1));

        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(orderRepository.findByUser(user)).thenReturn(Arrays.asList(order));
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("testUser");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        List<UserOrder> actualOrders = response.getBody();
        System.out.println(actualOrders.toArray());
        assertNotNull(actualOrders);
        assertEquals(new BigDecimal(1), actualOrders.get(0).getTotal());

        assertEquals(200, response.getStatusCodeValue());


    }
}
