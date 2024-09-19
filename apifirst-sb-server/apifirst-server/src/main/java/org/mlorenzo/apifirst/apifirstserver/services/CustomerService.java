package org.mlorenzo.apifirst.apifirstserver.services;

import org.mlorenzo.apifirst.model.CustomerDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDto> listCustomers();
    Optional<CustomerDto> getCustomerById(UUID customerId);
    CustomerDto createCustomer(CustomerDto customerDto);
    Optional<CustomerDto> updateCustomer(UUID customerId, CustomerDto customerDto);
}
