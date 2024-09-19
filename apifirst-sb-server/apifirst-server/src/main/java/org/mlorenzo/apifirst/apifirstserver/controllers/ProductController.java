package org.mlorenzo.apifirst.apifirstserver.controllers;

import lombok.RequiredArgsConstructor;
import org.mlorenzo.apifirst.apifirstserver.services.ProductService;
import org.mlorenzo.apifirst.model.ProductCreateDto;
import org.mlorenzo.apifirst.model.ProductDto;
import org.mlorenzo.apifirst.model.ProductUpdateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(ProductController.BASE_URL)
public class ProductController {
    public static final String BASE_URL = "/v1/products";

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> listProducts(){
        return ResponseEntity.ok(productService.listProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("productId") UUID uuid) {
        return productService.getProductById(uuid)
                // Versión simplificada de la expresión "product -> ResponseEntity.ok(product)"
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Product with id " + uuid + " not found"));
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody ProductCreateDto productCreateDto) {
        ProductDto savedProductDto = productService.createProduct(productCreateDto);

        UriComponents uriComponents = UriComponentsBuilder.fromPath(BASE_URL + "/{productId}")
                .buildAndExpand(savedProductDto.getId());

        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable UUID productId,
                                                    @RequestBody ProductUpdateDto productUpdateDto) {
        Optional<ProductDto> optionalProductDto = productService.updateProduct(productId, productUpdateDto);

        if(optionalProductDto.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id " + productId + " not found");

        return ResponseEntity.ok(optionalProductDto.get());
    }
}
