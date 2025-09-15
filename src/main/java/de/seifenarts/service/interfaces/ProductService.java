package de.seifenarts.service.interfaces;

import de.seifenarts.domain.dto.product_dto.request_dto.ProductRequestDto;
import de.seifenarts.domain.dto.product_dto.respons_dto.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Long addNewProduct(ProductRequestDto productRequestDto);

    Page getAllActiveProducts(Pageable pageable);

    ProductResponseDTO getProductById (Long productId);

    ProductResponseDTO setProductActiveStatus (Long productId);

    ProductResponseDTO updateProduct (Long productId, ProductRequestDto productRequestDto);

}
