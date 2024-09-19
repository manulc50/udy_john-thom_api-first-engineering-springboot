package org.mlorenzo.apifirst.apifirstserver.repositories;

import org.mlorenzo.apifirst.apifirstserver.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
