package de.seifenarts.service;

import de.seifenarts.domain.dto.payment_dto.request_dto.PaymentRequestDto;
import de.seifenarts.domain.dto.payment_dto.respons_dto.PaymentResponseDto;
import de.seifenarts.domain.entity.*;
import de.seifenarts.repository.OrderRepository;
import de.seifenarts.repository.PaymentRepository;
import de.seifenarts.service.interfaces.PaymentService;
import de.seifenarts.service.mapping.PaymentMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMappingService paymentMappingService;

    public PaymentServiceImpl(OrderRepository orderRepository, PaymentRepository paymentRepository, PaymentMappingService paymentMappingService) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.paymentMappingService = paymentMappingService;
    }

    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto dto) {
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found: " + dto.getOrderId()));
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new RuntimeException("Order not created");
        }

        Payment payment = new Payment();
        payment.setAmount(order.getTotalPrice());
        payment.setPaymentMethod(PaymentMethod.CARD);
        payment.setCreatedAt(LocalDateTime.now());
        payment.setResponse(null);
        payment.setOrder(order);
        payment.setProvider(Provider.KLARNA);
        payment.setUpdatedAt(null);
        payment.setStatus(PaymentStatus.PENDING);

        Payment savedPayment = paymentRepository.save(payment);
        return paymentMappingService.mapPaymentEntityToResponseDto(savedPayment);
    }

    @Override
    public PaymentResponseDto getPaymentById (Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + paymentId));

        return paymentMappingService.mapPaymentEntityToResponseDto(payment);
    }

    @Override
    public void updatePaymentStatus(Long paymentId, PaymentStatus status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found: " + paymentId));

        payment.setStatus(status);
        payment.setUpdatedAt(LocalDateTime.now());

        paymentRepository.save(payment);
    }

    @Override
    public void attachProviderResponse(Long paymentId, String response) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found: " + paymentId));

        payment.setResponse(response);
        paymentRepository.save(payment);
    }
}
