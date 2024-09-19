package org.mlorenzo.apifirst.apifirstserver.services;

import org.junit.jupiter.api.Test;
import org.mlorenzo.apifirst.apifirstserver.domain.Customer;
import org.mlorenzo.apifirst.apifirstserver.domain.PaymentMethod;
import org.mlorenzo.apifirst.apifirstserver.repositories.CustomerRepository;
import org.mlorenzo.apifirst.model.AddressDto;
import org.mlorenzo.apifirst.model.CustomerDto;
import org.mlorenzo.apifirst.model.NameDto;
import org.mlorenzo.apifirst.model.PaymentMethodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomerServiceImplTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    @Test
    void testCreateNewCustomer() {
        CustomerDto customerDto = createCustomerDTO();
        CustomerDto savedCustomer = customerService.createCustomer(customerDto);

        customerRepository.flush();

        assertNotNull(savedCustomer);
        assertNotNull(savedCustomer.getId());

        Customer customer = customerRepository.findById(savedCustomer.getId()).orElseThrow();

        assertNotNull(customer.getPaymentMethods());
        assertEquals(customerDto.getName().getFirstName(), customer.getName().getFirstName());
    }

    CustomerDto createCustomerDTO() {
        return CustomerDto.builder()
                .name(NameDto.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .build())
                .billToAddress(AddressDto.builder()
                        .addressLine1("1234 Main Street")
                        .city("San Diego")
                        .state("CA")
                        .zip("92101")
                        .build())
                .shipToAddress(AddressDto.builder()
                        .addressLine1("1234 Main Street")
                        .city("San Diego")
                        .state("CA")
                        .zip("92101")
                        .build())
                .email("joe@example.com")
                .phone("555-555-5555")
                .paymentMethods(Arrays.asList(PaymentMethodDto.builder()
                        .displayName("My Card")
                        .cardNumber(1234123412)
                        .expiryMonth(12)
                        .expiryYear(2020)
                        .cvv(123).build()))
                .build();
    }
}
