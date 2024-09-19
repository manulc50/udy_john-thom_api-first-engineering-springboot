package org.mlorenzo.apifirst.apifirstserver.repositories;

import org.mlorenzo.apifirst.apifirstserver.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
