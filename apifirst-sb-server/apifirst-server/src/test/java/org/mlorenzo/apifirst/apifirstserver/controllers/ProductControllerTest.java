package org.mlorenzo.apifirst.apifirstserver.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mlorenzo.apifirst.apifirstserver.domain.Product;
import org.mlorenzo.apifirst.model.DimensionsDto;
import org.mlorenzo.apifirst.model.ImageDto;
import org.mlorenzo.apifirst.model.ProductCreateDto;
import org.mlorenzo.apifirst.model.ProductUpdateDto;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest extends BaseTest {

    @DisplayName("Test List Products")
    @Test
    void testListProducts() throws Exception {
        mockMvc.perform(get(ProductController.BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThan(0)));
    }

    @DisplayName("Test Get By Id")
    @Test
    void testGetProductById() throws Exception {
        mockMvc.perform(get(ProductController.BASE_URL + "/{productId}", testProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testProduct.getId().toString()));
    }

    @DisplayName("Test Create New Product")
    @Test
    void testCreateProduct() throws Exception {
        ProductCreateDto newProduct = ProductCreateDto.builder()
                .description("New Product")
                .cost("5.00")
                .price("8.95")
                .categories(Arrays.asList("ELECTRONICS"))
                .images(Arrays.asList(ImageDto.builder()
                        .url("http://example.com/image.jpg")
                        .altText("Image Alt Text")
                        .build()))
                .dimensions(DimensionsDto.builder()
                        .length(10)
                        .width(10)
                        .height(10)
                        .build())
                .build();

        mockMvc.perform(post(ProductController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Transactional
    @DisplayName("Test Update Existing Product")
    @Test
    void testUpdateProduct() throws Exception {
        Product product = productRepository.findAll().iterator().next();

        ProductUpdateDto productUpdateDto = productMapper.productToUpdateDto(product);

        productUpdateDto.setDescription("Updated Description");

        mockMvc.perform(put(ProductController.BASE_URL + "/{productId}", product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", equalTo("Updated Description")));
    }
}
