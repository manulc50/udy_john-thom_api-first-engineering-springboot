package org.mlorenzo.apifirst.apifirstserver.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mlorenzo.apifirst.apifirstserver.domain.Order;
import org.mlorenzo.apifirst.model.OrderCreateDto;
import org.mlorenzo.apifirst.model.OrderLineCreateDto;
import org.mlorenzo.apifirst.model.OrderUpdateDto;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrderControllerTest extends BaseTest {

    @DisplayName("Test List Orders")
    @Test
    void testListOrders() throws Exception {
        mockMvc.perform(get(OrderController.BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThan(0)));
    }

    @DisplayName("Test Get By Id")
    @Test
    void testGetOrderById() throws Exception{
        mockMvc.perform(get(OrderController.BASE_URL + "/{OrderId}", testOrder.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testOrder.getId().toString()));
    }

    @Transactional
    @DisplayName("Test Create New Order")
    @Test
    void testCreateOrder() throws Exception {
        OrderCreateDto orderCreate = OrderCreateDto.builder()
                .customerId(testCustomer.getId())
                .selectPaymentMethodId(testCustomer.getPaymentMethods().get(0).getId())
                .orderLines(Collections.singletonList(OrderLineCreateDto.builder()
                        .productId(testProduct.getId())
                        .orderQuantity(1)
                        .build()))
                .build();

        System.out.println(objectMapper.writeValueAsString(orderCreate));

        mockMvc.perform(post(OrderController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderCreate)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Transactional
    @DisplayName("Test Update Existing Order")
    @Test
    void testUpdateOrder() throws Exception {
        Order order = orderRepository.findAll().get(0);

        order.getOrderLines().get(0).setOrderQuantity(222);

        OrderUpdateDto orderUpdateDto = orderMapper.orderToUpdateDto(order);

        mockMvc.perform(put(OrderController.BASE_URL + "/{orderId}", testOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(testOrder.getId().toString())))
                .andExpect(jsonPath("$.orderLines[0].orderQuantity", equalTo(222)));
    }
}
