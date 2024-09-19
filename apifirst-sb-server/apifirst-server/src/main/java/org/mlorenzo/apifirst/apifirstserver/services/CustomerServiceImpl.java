package org.mlorenzo.apifirst.apifirstserver.services;

import lombok.RequiredArgsConstructor;
import org.mlorenzo.apifirst.apifirstserver.domain.Customer;
import org.mlorenzo.apifirst.apifirstserver.mappers.CustomerMapper;
import org.mlorenzo.apifirst.apifirstserver.repositories.CustomerRepository;
import org.mlorenzo.apifirst.model.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDto> listCustomers() {
        return customerRepository.findAll().stream()
                // Versión simplificada de la expresiñon "customer -> customerMapper.customerToDto(customer)"
                .map(customerMapper::customerToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDto> getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId)
                // Versión simplificada de la expresiñon "customer -> customerMapper.customerToDto(customer)"
                .map(customerMapper::customerToDto);
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer savedCustomer = customerRepository.save(customerMapper.dtoToCustomer(customerDto));
        return customerMapper.customerToDto(savedCustomer);
    }

    @Override
    public Optional<CustomerDto> updateCustomer(UUID customerId, CustomerDto customerDto) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if(optionalCustomer.isEmpty())
            return Optional.empty();

        Customer customer = optionalCustomer.get();

        customerMapper.updateCustomer(customerDto, customer);

        Customer savedCustomer = customerRepository.save(customer);

        return Optional.of(customerMapper.customerToDto(savedCustomer));
    }
}
