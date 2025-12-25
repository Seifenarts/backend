package de.seifenarts.service.mapping;

import de.seifenarts.domain.dto.payment_dto.respons_dto.PaymentResponseDto;
import de.seifenarts.domain.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMappingService {

    @Mapping(target = "orderId", source = "order.id")
    PaymentResponseDto mapPaymentEntityToResponseDto(Payment payment);

}
