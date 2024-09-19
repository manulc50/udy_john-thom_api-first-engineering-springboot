package org.mlorenzo.apifirst.apifirstserver.services;

import lombok.RequiredArgsConstructor;
import org.mlorenzo.apifirst.apifirstserver.mappers.CategoryMapper;
import org.mlorenzo.apifirst.apifirstserver.repositories.CategoryRepository;
import org.mlorenzo.apifirst.model.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    public List<CategoryDto> listCategories() {
        return categoryRepository.findAll().stream()
                // Versión simplificada de la expresión "category -> categoryMapper.categoryToDto(category)"
                .map(categoryMapper::categoryToDto)
                .toList();
    }
}
