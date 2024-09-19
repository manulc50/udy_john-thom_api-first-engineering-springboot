package org.mlorenzo.apifirst.apifirstserver.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mlorenzo.apifirst.apifirstserver.domain.Customer;
import org.mlorenzo.apifirst.model.CustomerDto;

@Mapper
public interface CustomerMapper {
    CustomerDto customerToDto(Customer customer);
    Customer dtoToCustomer(CustomerDto customerDto);

    // Las propiedades "id", "dateCreated" y "dateUpdated" se ignoran en la actualización
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "dateUpdated", ignore = true)
    // Nota: Definimos este método para que MapStruct genere automáticamente las actualizaciones de las propiedades
    // en vez de hacerlas nosotros manualmente en la capa de servicio.
    void updateCustomer(CustomerDto customerDto, @MappingTarget Customer customer);
}
