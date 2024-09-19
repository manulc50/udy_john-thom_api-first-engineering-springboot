package org.mlorenzo.apifirst.apifirstserver.controllers;

import lombok.RequiredArgsConstructor;
import org.mlorenzo.apifirst.apifirstserver.services.OrderService;
import org.mlorenzo.apifirst.model.OrderCreateDto;
import org.mlorenzo.apifirst.model.OrderDto;
import org.mlorenzo.apifirst.model.OrderUpdateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mlorenzo.apifirst.apifirstserver.controllers.OrderController.BASE_URL;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_URL)
public class OrderController {
    public static final String BASE_URL = "/v1/orders";

    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> listOrders(){
        return orderService.listOrders();
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrderById(@PathVariable UUID orderId) {
        return orderService.getOrderById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Order with id " + orderId + " not found"));
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody OrderCreateDto orderCreateDto) {
        OrderDto savedOrderDto = orderService.createOrder(orderCreateDto);
        return ResponseEntity.created(URI.create(BASE_URL + "/" + savedOrderDto.getId())).build();
    }

    @PutMapping("/{orderId}")
    public OrderDto updateOrder(@PathVariable(value = "orderId") UUID id, @RequestBody OrderUpdateDto orderUpdateDto) {
        Optional<OrderDto> optionalOrderDto = orderService.updateOrder(id, orderUpdateDto);

        if(optionalOrderDto.isPresent())
            return optionalOrderDto.get();

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with id " + id + " not found");
    }
}
