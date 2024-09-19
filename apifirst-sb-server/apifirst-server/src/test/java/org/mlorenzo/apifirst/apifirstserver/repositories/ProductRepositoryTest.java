package org.mlorenzo.apifirst.apifirstserver.repositories;

import org.junit.jupiter.api.Test;
import org.mlorenzo.apifirst.apifirstserver.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Transactional(readOnly = true)
    @Test
    void testImagePersistence() {
        Product product = productRepository.findAll().iterator().next();

        assertNotNull(product);
        assertNotNull(product.getImages());
        assertTrue(product.getImages().size() > 0);
    }
}
