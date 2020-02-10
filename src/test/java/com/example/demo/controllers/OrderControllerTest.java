package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

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



    }

    @Test
    public void getOrdersForUser() {


    }
}
