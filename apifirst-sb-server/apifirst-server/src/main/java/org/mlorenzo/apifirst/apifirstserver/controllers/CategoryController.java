package org.mlorenzo.apifirst.apifirstserver.controllers;

import lombok.AllArgsConstructor;
import org.mlorenzo.apifirst.apifirstserver.services.CategoryService;
import org.mlorenzo.apifirst.model.CategoryDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.mlorenzo.apifirst.apifirstserver.controllers.CategoryController.BASE_URL;

@AllArgsConstructor
@RestController
@RequestMapping(BASE_URL)
public class CategoryController {
    public static final String BASE_URL = "/v1/categories";

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCategories() {
        return new ResponseEntity<>(categoryService.listCategories(), HttpStatus.OK);
    }
}
