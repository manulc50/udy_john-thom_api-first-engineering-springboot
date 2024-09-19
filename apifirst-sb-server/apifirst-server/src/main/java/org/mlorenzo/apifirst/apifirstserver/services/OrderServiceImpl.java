package org.mlorenzo.apifirst.apifirstserver.services;

import lombok.AllArgsConstructor;
import org.mlorenzo.apifirst.apifirstserver.domain.*;
import org.mlorenzo.apifirst.apifirstserver.mappers.OrderMapper;
import org.mlorenzo.apifirst.apifirstserver.repositories.OrderRepository;
import org.mlorenzo.apifirst.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderDto> listOrders() {
        return orderRepository.findAll().stream()
                // Versión simplificada de la expresión "order -> orderMapper.orderToDto(order)"
                .map(orderMapper::orderToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderDto> getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                // Versión simplificada de la expresión "order -> orderMapper.orderToDto(order)"
                .map(orderMapper::orderToDto);
    }

    @Override
    public OrderDto createOrder(OrderCreateDto orderCreateDto) {
        Order savedOrder = orderRepository.saveAndFlush(orderMapper.dtoToOrder(orderCreateDto));
        return orderMapper.orderToDto(savedOrder);
    }

    @Override
    public Optional<OrderDto> updateOrder(UUID orderId, OrderUpdateDto orderUpdateDto) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    orderMapper.updateOrder(orderUpdateDto, order);
                    Order savedOrder = orderRepository.save(order);
                    return orderMapper.orderToDto(savedOrder);
                });
    }
}
