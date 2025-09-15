package de.seifenarts.service.mapping;
import de.seifenarts.domain.dto.product_dto.request_dto.ProductRequestDto;
import de.seifenarts.domain.dto.product_dto.respons_dto.ProductResponseDTO;
import de.seifenarts.domain.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProductMappingService {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "aromas", ignore = true)
    @Mapping(target = "images", ignore = true)
    Product mapRequestDtoToEntity(ProductRequestDto dto);

    @Mapping(target = "aromas", ignore = true)
    @Mapping(target = "imageUrls", ignore = true)
    ProductResponseDTO mapEntityToProductResponsDTO(Product product);
}


