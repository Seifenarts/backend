package de.seifenarts.service.interfaces;

import de.seifenarts.domain.dto.product_dto.request_dto.ProductRequestDto;
import de.seifenarts.domain.dto.product_dto.respons_dto.ProductResponsDTO;
import de.seifenarts.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Long addNewProduct(ProductRequestDto productRequestDto);

    Page getAllActiveProducts(Pageable pageable);

    ProductResponsDTO getProductById (Long productId);

    ProductResponsDTO setProductActiveStatus (Long productId);

}
