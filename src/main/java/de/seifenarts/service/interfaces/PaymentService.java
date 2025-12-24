package de.seifenarts.service.interfaces;

import de.seifenarts.domain.entity.Payment;
import de.seifenarts.domain.entity.PaymentStatus;

public interface PaymentService {

    Long createPayment(Long orderId);

    void updatePaymentStatus(Long paymentId, PaymentStatus status);

    void attachProviderResponse(Long paymentId, String response);
}
