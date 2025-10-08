package de.seifenarts.repository;

import de.seifenarts.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"images", "aromas"})
    @Query("SELECT p FROM Product p WHERE p.active = true")
    Page<Product> findAllActiveProducts(Pageable pageable);

}
