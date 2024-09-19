package org.mlorenzo.apifirst.apifirstserver.services;

import org.mlorenzo.apifirst.model.OrderCreateDto;
import org.mlorenzo.apifirst.model.OrderDto;
import org.mlorenzo.apifirst.model.OrderUpdateDto;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    List<OrderDto> listOrders();
    Optional<OrderDto> getOrderById(UUID orderId);
    OrderDto createOrder(OrderCreateDto orderCreateDto);
    Optional<OrderDto> updateOrder(UUID orderId, OrderUpdateDto orderUpdateDto);
}
