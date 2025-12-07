package de.seifenarts.repository;

import de.seifenarts.domain.entity.OrderProduct;
import de.seifenarts.domain.composite_key.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
}
