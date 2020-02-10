package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private static ItemController itemController;

    private static ItemRepository itemRepository = mock(ItemRepository.class);

    @BeforeAll
    public static void setUp() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
    }


    @Test
    public void getItems() {
        Item item1 = new Item();
        item1.setId(1L);
        item1.setName("item1");
        item1.setPrice(new BigDecimal("1.1"));
        item1.setDescription("description 1");

        Item item2 = new Item();
        item2.setId(2L);
        item2.setName("item2");
        item2.setPrice(new BigDecimal("1.2"));
        item2.setDescription("description 2");

        Item item3 = new Item();
        item3.setId(3L);
        item3.setName("item3");
        item3.setPrice(new BigDecimal("1.3"));
        item3.setDescription("description 3");

        when(itemRepository.findAll()).thenReturn(Arrays.asList(item1, item2,item3));

        ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        List<Item> items = response.getBody();
        assertEquals(3, items.size());
        assertEquals(200, response.getStatusCodeValue());


    }

    @Test
    public void getItemById() {
        Item item1 = new Item();
        item1.setId(1L);
        item1.setName("item1");
        item1.setPrice(new BigDecimal("1.1"));
        item1.setDescription("description");

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item1));

        ResponseEntity<Item> response = itemController.getItemById(1L);
        assertNotNull(response);
        Item item = response.getBody();
        assertEquals(1, (long)item.getId());
        assertEquals("item1", item.getName());
        assertEquals("1.1", item.getPrice().toString());
        assertEquals("description", item.getDescription());
        assertEquals(200, response.getStatusCodeValue());


    }

    @Test
    public void getItemsByName() {
        Item item1 = new Item();
        item1.setId(1L);
        item1.setName("item");
        item1.setPrice(new BigDecimal("1.1"));
        item1.setDescription("description");

        Item item2 = new Item();
        item1.setId(2L);
        item1.setName("item");
        item1.setPrice(new BigDecimal("1.2"));
        item1.setDescription("description");

        when(itemRepository.findByName("item")).thenReturn(Arrays.asList(item1, item2));

        ResponseEntity<List<Item>> response = itemController.getItemsByName("item");
        assertNotNull(response);
        List<Item> items = response.getBody();
        assertEquals(2, items.size());
        assertEquals(200, response.getStatusCodeValue());
    }
}
