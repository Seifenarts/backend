package de.seifenarts.service.mapping;

import de.seifenarts.domain.dto.payment_dto.respons_dto.PaymentResponseDto;
import de.seifenarts.domain.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMappingService {

    PaymentResponseDto mapPaymentEntityToResponseDto(Payment payment);

}
