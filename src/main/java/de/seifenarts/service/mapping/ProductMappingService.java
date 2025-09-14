package de.seifenarts.service.mapping;
import de.seifenarts.domain.dto.product_dto.request_dto.ProductRequestDto;
import de.seifenarts.domain.entity.Image;
import de.seifenarts.domain.entity.Product;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface ProductMappingService {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "aromas", ignore = true)
    @Mapping(target = "images", ignore = true)
    Product mapRequestDtoToEntity(ProductRequestDto dto);
}

