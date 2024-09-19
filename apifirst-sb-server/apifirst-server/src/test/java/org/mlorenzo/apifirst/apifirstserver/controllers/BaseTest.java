package org.mlorenzo.apifirst.apifirstserver.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import org.junit.jupiter.api.BeforeEach;
import org.mlorenzo.apifirst.apifirstserver.domain.Customer;
import org.mlorenzo.apifirst.apifirstserver.domain.Order;
import org.mlorenzo.apifirst.apifirstserver.domain.Product;
import org.mlorenzo.apifirst.apifirstserver.mappers.CustomerMapper;
import org.mlorenzo.apifirst.apifirstserver.mappers.OrderMapper;
import org.mlorenzo.apifirst.apifirstserver.mappers.ProductMapper;
import org.mlorenzo.apifirst.apifirstserver.repositories.CustomerRepository;
import org.mlorenzo.apifirst.apifirstserver.repositories.OrderRepository;
import org.mlorenzo.apifirst.apifirstserver.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class BaseTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    Filter validationFilter;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    OrderMapper orderMapper;

    MockMvc mockMvc;
    Customer testCustomer;
    Product testProduct;
    Order testOrder;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(validationFilter)
                .build();

        testCustomer = customerRepository.findAll().iterator().next();
        testProduct = productRepository.findAll().iterator().next();
        testOrder = orderRepository.findAll().iterator().next();
    }
}
