package de.seifenarts.service.interfaces;

import org.springframework.http.ResponseEntity;

public interface KlarnaPaymentService {

    void handleWebhook(String payload);
}
