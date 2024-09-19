package org.mlorenzo.apifirst.apifirstserver.services;

import org.mlorenzo.apifirst.model.ProductCreateDto;
import org.mlorenzo.apifirst.model.ProductDto;
import org.mlorenzo.apifirst.model.ProductUpdateDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    List<ProductDto> listProducts();
    Optional<ProductDto> getProductById(UUID productId);
    ProductDto createProduct(ProductCreateDto productCreateDto);
    Optional<ProductDto> updateProduct(UUID productId, ProductUpdateDto productUpdateDto);
}
