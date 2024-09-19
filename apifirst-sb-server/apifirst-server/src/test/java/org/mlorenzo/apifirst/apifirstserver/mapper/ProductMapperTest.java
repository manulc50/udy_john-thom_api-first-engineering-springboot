package org.mlorenzo.apifirst.apifirstserver.mapper;

import org.junit.jupiter.api.Test;
import org.mlorenzo.apifirst.apifirstserver.domain.Category;
import org.mlorenzo.apifirst.apifirstserver.domain.Product;
import org.mlorenzo.apifirst.apifirstserver.mappers.ProductMapper;
import org.mlorenzo.apifirst.apifirstserver.repositories.CategoryRepository;
import org.mlorenzo.apifirst.model.DimensionsDto;
import org.mlorenzo.apifirst.model.ImageDto;
import org.mlorenzo.apifirst.model.ProductCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductMapperTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductMapper productMapper;

    @Test
    void testDtoToProduct() {
        // fail if no category found
        Category category = categoryRepository.findByCategoryCode("ELECTRONICS").orElseThrow();

        ProductCreateDto productCreateDto = buildProductCreateDto(category.getCategoryCode());

        Product product = productMapper.dtoToProduct(productCreateDto);

        assertNotNull(product);
        assertEquals(productCreateDto.getDescription(), product.getDescription());
        assertEquals(productCreateDto.getCost(), product.getCost());
        assertEquals(productCreateDto.getPrice(), product.getPrice());
        assertEquals(productCreateDto.getDimensions().getHeight(), product.getDimensions().getHeight());
        assertEquals(productCreateDto.getDimensions().getWidth(), product.getDimensions().getWidth());
        assertEquals(productCreateDto.getDimensions().getLength(), product.getDimensions().getLength());
        assertEquals(productCreateDto.getImages().get(0).getUrl(), product.getImages().get(0).getUrl());
        assertEquals(productCreateDto.getImages().get(0).getAltText(), product.getImages().get(0).getAltText());
        assertEquals(productCreateDto.getCategories().get(0), product.getCategories().get(0).getCategoryCode());

        // test to catch changes, fail test if fields are added
        assertEquals(9, product.getClass().getDeclaredFields().length);

    }

    ProductCreateDto buildProductCreateDto(String cat) {
        return ProductCreateDto.builder()
                .price("1.0")
                .description("description")
                .images(Collections.singletonList(ImageDto.builder()
                        .url("http://example.com/image.jpg")
                        .altText("Image Alt Text")
                        .build()))
                .categories(Collections.singletonList(cat))
                .cost("1.0")
                .dimensions(DimensionsDto.builder()
                        .height(1)
                        .length(1)
                        .width(1)
                        .build())
                .build();
    }
}
