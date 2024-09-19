package org.mlorenzo.apifirst.apifirstserver.controllers;

import lombok.AllArgsConstructor;
import org.mlorenzo.apifirst.apifirstserver.services.CustomerService;
import org.mlorenzo.apifirst.model.CustomerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mlorenzo.apifirst.apifirstserver.controllers.CustomerController.BASE_URL;

@AllArgsConstructor
@RestController
@RequestMapping(BASE_URL)
public class CustomerController {
    public static final String BASE_URL = "/v1/customers";

    private final CustomerService customerService;

    @GetMapping
    public List<CustomerDto> listCustomers() {
        return customerService.listCustomers();
    }

    @GetMapping("{customerId}")
    public CustomerDto getCustomerById(@PathVariable UUID customerId) {
        return customerService.getCustomerById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Customer with id " + customerId + " not found"));
    }

    @PostMapping
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto savedCustomerDto = customerService.createCustomer(customerDto);
        return ResponseEntity.created(URI.create(BASE_URL + "/" + savedCustomerDto.getId())).build();
    }

    @PutMapping("{customerId}")
    public CustomerDto updateCustomer(@PathVariable UUID customerId, @RequestBody CustomerDto customerDto) {
        Optional<CustomerDto> optionalCustomerDto = customerService.updateCustomer(customerId, customerDto);

        if(optionalCustomerDto.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with id " + customerId + " not found");

        return optionalCustomerDto.get();
    }
}
