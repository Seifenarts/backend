package de.seifenarts.service.interfaces;

import de.seifenarts.domain.dto.payment_dto.request_dto.PaymentRequestDto;
import de.seifenarts.domain.dto.payment_dto.respons_dto.PaymentResponseDto;
import de.seifenarts.domain.entity.PaymentStatus;

public interface PaymentService {

    PaymentResponseDto createPayment(PaymentRequestDto dto);

    PaymentResponseDto getPaymentById(Long paymentId);

    void updatePaymentStatus(Long paymentId, PaymentStatus status);

    void attachProviderResponse(Long paymentId, String response);

}
