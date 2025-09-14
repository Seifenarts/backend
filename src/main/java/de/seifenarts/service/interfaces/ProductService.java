package de.seifenarts.service.interfaces;

import de.seifenarts.domain.dto.product_dto.request_dto.ProductRequestDto;

public interface ProductService {

    Long addNewProduct(ProductRequestDto productRequestDto);

}
