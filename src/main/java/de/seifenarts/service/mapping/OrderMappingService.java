package de.seifenarts.service.mapping;


import de.seifenarts.domain.dto.order_dto.request_dto.OrderRequestDto;
import de.seifenarts.domain.dto.order_dto.response_dto.OrderResponseDto;
import de.seifenarts.domain.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMappingService {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "orderProducts", ignore = true)
    @Mapping(target = "payments", ignore = true)
    Order mapRequestDtoToEntity(OrderRequestDto dto);

    OrderResponseDto mapEntityToResponseDto(Order order);
}
