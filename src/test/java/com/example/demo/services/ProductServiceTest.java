package com.example.demo.services;

import com.example.demo.Repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    private LineItemService lineItemService;
    @InjectMocks
    private ProductService underTest;

    @BeforeEach
    void setUp() {
        underTest=new ProductService(productRepository,lineItemService);
    }

    @Test
    void getProduct() {
    }

    @Test
    void postProduct() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void postProductInLineItem() {
    }
}