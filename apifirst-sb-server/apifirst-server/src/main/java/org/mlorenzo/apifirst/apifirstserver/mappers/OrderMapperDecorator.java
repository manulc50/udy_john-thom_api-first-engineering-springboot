package org.mlorenzo.apifirst.apifirstserver.mappers;

import org.mlorenzo.apifirst.apifirstserver.domain.*;
import org.mlorenzo.apifirst.apifirstserver.repositories.CustomerRepository;
import org.mlorenzo.apifirst.apifirstserver.repositories.ProductRepository;
import org.mlorenzo.apifirst.model.OrderCreateDto;
import org.mlorenzo.apifirst.model.OrderDto;
import org.mlorenzo.apifirst.model.OrderUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.UUID;

public abstract class OrderMapperDecorator implements OrderMapper {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    @Qualifier("delegate")
    private OrderMapper delegateOrderMapper;

    @Autowired
    private PaymentMethodMapper paymentMethodMapper;

    @Override
    public Order dtoToOrder(OrderCreateDto orderCreateDto) {
        Customer orderCustomer = customerRepository.findById(orderCreateDto.getCustomerId()).orElseThrow();

        PaymentMethod selectedPaymentMethod = selectPaymentMethod(orderCreateDto.getSelectPaymentMethodId(),
                orderCustomer.getPaymentMethods());

        Order.OrderBuilder builder = Order.builder()
                .customer(orderCustomer)
                .selectedPaymentMethod(selectedPaymentMethod)
                .orderStatus(OrderStatus.NEW);

        List<OrderLine> orderLines = orderCreateDto.getOrderLines().stream()
                .map(orderLine -> {
                    Product product = productRepository.findById(orderLine.getProductId()).orElseThrow();

                    return OrderLine.builder()
                            .product(product)
                            .orderQuantity(orderLine.getOrderQuantity())
                            .build();
                })
                .toList();

        Order order = builder.orderLines(orderLines).build();

        // Relación bidireccional
        orderLines.forEach(orderLine -> orderLine.setOrder(order));

        return order;
    }

    @Override
    public OrderDto orderToDto(Order order) {
        OrderDto orderDto = delegateOrderMapper.orderToDto(order);

        orderDto.getCustomer()
                .selectedPaymentMethod(paymentMethodMapper.paymentMethodToDto(order.getSelectedPaymentMethod()));

        return orderDto;
    }

    @Override
    public OrderUpdateDto orderToUpdateDto(Order order) {
        OrderUpdateDto orderUpdateDto = delegateOrderMapper.orderToUpdateDto(order);

        orderUpdateDto.setCustomerId(order.getCustomer().getId());
        orderUpdateDto.setSelectPaymentMethodId(order.getSelectedPaymentMethod().getId());

        orderUpdateDto.getOrderLines().forEach(orderLineUpdateDto -> {
            OrderLine existingOrderLine = order.getOrderLines().stream()
                    .filter(orderLine -> orderLine.getId().equals(orderLineUpdateDto.getId()))
                    .findFirst().orElseThrow();

            orderLineUpdateDto.setProductId(existingOrderLine.getProduct().getId());
        });

        return orderUpdateDto;
    }

    @Override
    public void updateOrder(OrderUpdateDto orderUpdateDto, Order order) {
        delegateOrderMapper.updateOrder(orderUpdateDto, order);

        Customer customer = customerRepository.findById(orderUpdateDto.getCustomerId()).orElseThrow();

        order.setCustomer(customer);

        PaymentMethod selectedPaymentMethod = selectPaymentMethod(orderUpdateDto.getSelectPaymentMethodId(),
                customer.getPaymentMethods());

        order.setSelectedPaymentMethod(selectedPaymentMethod);

        orderUpdateDto.getOrderLines().forEach(orderLineUpdateDto -> {
            OrderLine existingOrderLine = order.getOrderLines().stream()
                    .filter(orderLine -> orderLine.getId().equals(orderLineUpdateDto.getId()))
                    .findFirst().orElseThrow();

            Product product = productRepository.findById(orderLineUpdateDto.getProductId()).orElseThrow();

            existingOrderLine.setProduct(product);
            existingOrderLine.setOrderQuantity(orderLineUpdateDto.getOrderQuantity());
        });

    }

    private PaymentMethod selectPaymentMethod(UUID paymentMethodId, List<PaymentMethod> paymentMethods) {
        return paymentMethods.stream()
                .filter(paymentMethod -> paymentMethod.getId().equals(paymentMethodId))
                .findFirst()
                .orElseThrow();
    }
}
