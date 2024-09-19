package org.mlorenzo.apifirst.apifirstserver.mappers;

import org.mapstruct.Mapper;
import org.mlorenzo.apifirst.apifirstserver.domain.Category;
import org.mlorenzo.apifirst.model.CategoryDto;

@Mapper
public interface CategoryMapper {
    CategoryDto categoryToDto(Category category);
    Category dtoToCategory(CategoryDto categoryDto);
}
