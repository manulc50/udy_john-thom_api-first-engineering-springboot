package org.mlorenzo.apifirst.apifirstserver.services;

import lombok.AllArgsConstructor;
import org.mlorenzo.apifirst.apifirstserver.domain.Product;
import org.mlorenzo.apifirst.apifirstserver.mappers.ProductMapper;
import org.mlorenzo.apifirst.apifirstserver.repositories.ProductRepository;
import org.mlorenzo.apifirst.model.ProductCreateDto;
import org.mlorenzo.apifirst.model.ProductDto;
import org.mlorenzo.apifirst.model.ProductUpdateDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDto> listProducts() {
        return productRepository.findAll().stream()
                // Versión simplificada de la expresión "product -> productMapper.productToDto(product)"
                .map(productMapper::productToDto)
                .toList();
    }

    @Override
    public Optional<ProductDto> getProductById(UUID productId) {
        return productRepository.findById(productId)
                // Versión simplificada de la expresión "product -> productMapper.productToDto(product)"
                .map(productMapper::productToDto);
    }

    @Override
    public ProductDto createProduct(ProductCreateDto productCreateDto) {
        Product savedProduct = productRepository.save(productMapper.dtoToProduct(productCreateDto));
        return productMapper.productToDto(savedProduct);
    }

    @Override
    public Optional<ProductDto> updateProduct(UUID productId, ProductUpdateDto productUpdateDto) {
        return productRepository.findById(productId)
                .map(product -> {
                    productMapper.updateProduct(productUpdateDto, product);
                    return productRepository.save(product);
                })
                // Versión simplificada de la expresión "product -> productMapper.productToDto(product)"
                .map(productMapper::productToDto);
    }
}
