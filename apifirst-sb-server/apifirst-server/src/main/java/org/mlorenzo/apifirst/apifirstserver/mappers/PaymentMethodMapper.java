package org.mlorenzo.apifirst.apifirstserver.mappers;

import org.mapstruct.Mapper;
import org.mlorenzo.apifirst.apifirstserver.domain.PaymentMethod;
import org.mlorenzo.apifirst.model.PaymentMethodDto;

@Mapper
public interface PaymentMethodMapper {
    PaymentMethodDto paymentMethodToDto(PaymentMethod paymentMethod);
}
