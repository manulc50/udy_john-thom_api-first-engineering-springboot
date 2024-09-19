package org.mlorenzo.apifirst.apifirstserver.mappers;

import org.mlorenzo.apifirst.apifirstserver.domain.Category;
import org.mlorenzo.apifirst.apifirstserver.domain.Image;
import org.mlorenzo.apifirst.apifirstserver.domain.Product;
import org.mlorenzo.apifirst.apifirstserver.repositories.CategoryRepository;
import org.mlorenzo.apifirst.apifirstserver.repositories.ImageRepository;
import org.mlorenzo.apifirst.model.ProductCreateDto;
import org.mlorenzo.apifirst.model.ProductDto;
import org.mlorenzo.apifirst.model.ProductUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ProductMapperDecorator implements ProductMapper {

    @Autowired
    @Qualifier("delegate")
    private ProductMapper productMapperDelegate;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Product dtoToProduct(ProductCreateDto productCreateDto) {
        Product product = productMapperDelegate.dtoToProduct(productCreateDto);

        if(productCreateDto.getCategories() != null) {
            List<Category> categories = categoryCodesToCategories(productCreateDto.getCategories());

            product.setCategories(categories);
        }

        return product;
    }

    @Override
    public ProductDto productToDto(Product product) {
        return productMapperDelegate.productToDto(product);
    }

    @Override
    public ProductUpdateDto productToUpdateDto(Product product) {
        ProductUpdateDto productUpdateDto = productMapperDelegate.productToUpdateDto(product);

        if(product.getCategories() != null) {
            List<String> categoryCodes = product.getCategories().stream()
                    // Versión simplificada de la expresión "category -> category.getCategoryCode()"
                    .map(Category::getCategoryCode)
                    .collect(Collectors.toList());

            productUpdateDto.setCategories(categoryCodes);
        }

        return productUpdateDto;
    }

    @Override
    public void updateProduct(ProductUpdateDto productUpdateDto, Product product) {
        productMapperDelegate.updateProduct(productUpdateDto, product);

        if(productUpdateDto.getImages() != null) {
            List<Image> images = productUpdateDto.getImages().stream()
                    .map(productImageUpdateDto -> {
                        Image image = imageRepository.findById(productImageUpdateDto.getId()).orElseThrow();
                        imageMapper.updateImage(productImageUpdateDto, image);
                        return image;
                    })
                    .collect(Collectors.toList());

            product.setImages(images);
        }

        if(productUpdateDto.getCategories() != null) {
            List<Category> categories = categoryCodesToCategories(productUpdateDto.getCategories());

            product.setCategories(categories);
        }
    }

    private List<Category> categoryCodesToCategories(List<String> categoryCodes) {
        return categoryCodes.stream()
                .map(categoryCode -> categoryRepository.findByCategoryCode(categoryCode).orElseThrow())
                .collect(Collectors.toList());
    }
}
