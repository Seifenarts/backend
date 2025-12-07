package de.seifenarts.service.mapping;

import de.seifenarts.domain.dto.customer_dto.request_dto.CustomerRequestDto;
import de.seifenarts.domain.entity.Customer;
import de.seifenarts.domain.entity.DeliveryMethod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMappingService {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "name", expression = "java(dto.getFirstName() + \" \" + dto.getLastName())")
    Customer mapRequestDtoToEntity(CustomerRequestDto dto);

    default DeliveryMethod toEnum(String value) {
        if (value == null) return null;
        return DeliveryMethod.valueOf(value.toUpperCase());
    }
}

