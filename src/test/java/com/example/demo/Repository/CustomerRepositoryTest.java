package com.example.demo.Repository;

import com.example.demo.Tables.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository underTest;
    @AfterEach
    void tearDown() {underTest.deleteAll();}
    @Test
    void checkToFindCustomerByEmail() {
        Customer customer=new Customer("Hassan","hassan@gmail.com","password");
        underTest.save(customer);
        Customer testedCustomer=underTest.findCustomerByEmail(customer.getEmail()).get();
        assertEquals(testedCustomer,customer);
    }
}