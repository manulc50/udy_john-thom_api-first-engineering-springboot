package org.mlorenzo.apifirst.apifirstserver.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mlorenzo.apifirst.apifirstserver.domain.Product;
import org.mlorenzo.apifirst.model.ProductCreateDto;
import org.mlorenzo.apifirst.model.ProductDto;
import org.mlorenzo.apifirst.model.ProductUpdateDto;

@Mapper
@DecoratedWith(ProductMapperDecorator.class)
public interface ProductMapper {

    // Ignoramos la propiedad "categories" porque se mapea de forma personalizada en la clase ProductMapperDecorator.
    @Mapping(target = "categories", ignore = true)
    Product dtoToProduct(ProductCreateDto productCreateDto);

    ProductDto productToDto(Product product);

    // Ignoramos la propiedad "categories" porque se mapea de forma personalizada en la clase ProductMapperDecorator.
    @Mapping(target = "categories", ignore = true)
    ProductUpdateDto productToUpdateDto(Product product);

    // Ignoramos las propiedades "images" y "categories" porque se mapean de forma personalizada en la
    // clase ProductMapperDecorator.
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "categories", ignore = true)
    // Nota: Definimos este método para que MapStruct genere automáticamente las actualizaciones de las propiedades
    // en vez de hacerlas nosotros manualmente en la capa de servicio.
    void updateProduct(ProductUpdateDto productUpdateDto, @MappingTarget Product product);
}
