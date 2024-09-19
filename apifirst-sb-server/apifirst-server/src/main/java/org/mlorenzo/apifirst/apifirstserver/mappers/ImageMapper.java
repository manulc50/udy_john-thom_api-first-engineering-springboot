package org.mlorenzo.apifirst.apifirstserver.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mlorenzo.apifirst.apifirstserver.domain.Image;
import org.mlorenzo.apifirst.model.ProductImageUpdateDto;

@Mapper
public interface ImageMapper {

    // La propiedad "id" no la queremos actualizar
    @Mapping(target = "id", ignore = true)
    // Nota: Definimos este método para que MapStruct genere automáticamente las actualizaciones de las propiedades
    // en vez de hacerlas nosotros manualmente en la capa de servicio.
    void updateImage(ProductImageUpdateDto productImageUpdateDto, @MappingTarget Image image);
}
