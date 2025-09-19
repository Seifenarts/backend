package de.seifenarts.repository;

import de.seifenarts.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByProductIdIn(List<Long> productIds);

}
