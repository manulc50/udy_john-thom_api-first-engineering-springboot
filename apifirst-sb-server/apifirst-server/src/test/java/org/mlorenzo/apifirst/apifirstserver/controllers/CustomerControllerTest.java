package org.mlorenzo.apifirst.apifirstserver.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mlorenzo.apifirst.apifirstserver.domain.Address;
import org.mlorenzo.apifirst.apifirstserver.domain.Customer;
import org.mlorenzo.apifirst.apifirstserver.domain.Name;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class CustomerControllerTest extends BaseTest {

    @DisplayName("Test List Customers")
    @Test
    void testListCustomers() throws Exception {
        mockMvc.perform(get(CustomerController.BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThan(0)));
    }

    @DisplayName("Test Get By Id")
    @Test
    void testGetCustomerById() throws Exception {
        mockMvc.perform(get(CustomerController.BASE_URL + "/{customerId}", testCustomer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testCustomer.getId().toString()));
    }

    @DisplayName("Test Create New Customer")
    @Test
    void testCreateCustomer() throws Exception {
        Customer newCustomer = Customer.builder()
                .name(Name.builder()
                        .lastName("Doe")
                        .firstName("John")
                        .build())
                .phone("555-555-5555")
                .email("john@example.com")
                .shipToAddress(Address.builder()
                        .addressLine1("123 Main St")
                        .city("Denver")
                        .state("CO")
                        .zip("80216")
                        .build())
                .billToAddress(Address.builder()
                        .addressLine1("123 Main St")
                        .city("Denver")
                        .state("CO")
                        .zip("80216")
                        .build())
                .build();

        mockMvc.perform(post(CustomerController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCustomer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Transactional
    @DisplayName("Test Update Existing Customer")
    @Test
    void testUpdateCustomer() throws Exception {
        Customer customer = customerRepository.findAll().iterator().next();

        customer.getName().setFirstName("Updated");
        customer.getName().setLastName("Updated2");
        customer.getPaymentMethods().get(0).setDisplayName("NEW NAME");

        mockMvc.perform(put(CustomerController.BASE_URL + "/{customerId}", testCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMapper.customerToDto(customer))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name.firstName", equalTo("Updated")))
                .andExpect(jsonPath("$.name.lastName", equalTo("Updated2")))
                .andExpect(jsonPath("$.paymentMethods[0].displayName", equalTo("NEW NAME")));
    }
}
