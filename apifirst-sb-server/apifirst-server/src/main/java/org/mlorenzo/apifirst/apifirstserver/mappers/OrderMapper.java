package org.mlorenzo.apifirst.apifirstserver.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mlorenzo.apifirst.apifirstserver.domain.Order;
import org.mlorenzo.apifirst.model.OrderCreateDto;
import org.mlorenzo.apifirst.model.OrderDto;
import org.mlorenzo.apifirst.model.OrderUpdateDto;

@DecoratedWith(OrderMapperDecorator.class)
@Mapper
public interface OrderMapper {
    Order dtoToOrder(OrderCreateDto orderCreateDto);
    OrderDto orderToDto(Order order);
    OrderUpdateDto orderToUpdateDto(Order order);
    void updateOrder(OrderUpdateDto orderUpdateDto, @MappingTarget Order order);
}
