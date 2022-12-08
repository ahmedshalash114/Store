package com.example.demo.Repository;

import com.example.demo.Tables.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ProductRepositoryTest {
@Autowired
private ProductRepository underTest;
    @AfterEach
    void tearDown() {underTest.deleteAll();}
    @Test
    void findProductByName() {
        Product product=new Product("product",123,"description");
        underTest.save(product);
        Product testProduct=underTest.findProductByName(product.getName()).get();
        assertEquals(testProduct,product);
    }
}