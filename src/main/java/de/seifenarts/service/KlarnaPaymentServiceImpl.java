package de.seifenarts.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.seifenarts.domain.dto.payment_dto.respons_dto.PaymentResponseDto;
import de.seifenarts.domain.entity.PaymentStatus;
import de.seifenarts.service.interfaces.KlarnaPaymentService;
import de.seifenarts.service.interfaces.OrderService;
import de.seifenarts.service.interfaces.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KlarnaPaymentServiceImpl implements KlarnaPaymentService {

    private final ObjectMapper objectMapper;
    private final PaymentService paymentService;
    private final OrderService orderService;

    @Override
    public void handleWebhook(String payload) {
        try {
            JsonNode root = objectMapper.readTree(payload);

            String eventType = root.path("event_type").asText();
            JsonNode orderNode = root.path("order");

            String merchantRef1 = orderNode.path("merchant_reference_1").asText();
            String merchantRef2 = orderNode.path("merchant_reference_2").asText();

            Long paymentId = Long.valueOf(merchantRef1);
            Long orderId = Long.valueOf(merchantRef2);
            PaymentStatus paymentStatus = mapKlarnaStatus(eventType);

            PaymentResponseDto payment = paymentService.getPaymentById(paymentId);

            if (payment.getStatus() == paymentStatus) {
                return;
            }

            paymentService.attachProviderResponse(paymentId, payload);
            paymentService.updatePaymentStatus(paymentId, paymentStatus);

            if (paymentStatus == PaymentStatus.CAPTURED) {
                orderService.markOrderPaid(orderId, paymentId);
            }

            if (paymentStatus == PaymentStatus.CANCELLED || paymentStatus == PaymentStatus.FAILED) {
                orderService.markOrderCancelled(orderId);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to process Klarna webhook", e);
        }
    }

    private PaymentStatus mapKlarnaStatus(String eventType) {
        return switch (eventType) {
            case "ORDER_AUTHORIZED" -> PaymentStatus.AUTHORIZED;
            case "ORDER_CAPTURED" -> PaymentStatus.CAPTURED;
            case "ORDER_CANCELLED" -> PaymentStatus.CANCELLED;
            case "ORDER_FAILED" -> PaymentStatus.FAILED;
            case "ORDER_REFUNDED" -> PaymentStatus.REFUNDED;
            default -> throw new IllegalArgumentException("Unknown Klarna event: " + eventType);

        };
    }
}

        


