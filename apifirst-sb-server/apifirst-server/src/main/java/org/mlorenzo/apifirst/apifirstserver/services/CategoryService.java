package org.mlorenzo.apifirst.apifirstserver.services;

import org.mlorenzo.apifirst.model.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> listCategories();
}
