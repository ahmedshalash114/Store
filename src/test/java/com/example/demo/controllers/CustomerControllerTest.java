package com.example.demo.controllers;
import com.example.demo.Repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    @MockBean
    private CustomerRepository customerRepository;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void shouldGetCustomer() throws Exception {
    }
    @Test
    void postCustomer() throws Exception{
    }

    @Test
    void deleteCustomer() throws Exception {
    }

    @Test
    void updateCustomer() throws Exception{
    }
}