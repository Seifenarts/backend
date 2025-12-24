package de.seifenarts.service;

import de.seifenarts.domain.entity.*;
import de.seifenarts.repository.OrderRepository;
import de.seifenarts.repository.PaymentRepository;
import de.seifenarts.service.interfaces.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Long createPayment(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
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

        paymentRepository.save(payment);

        return payment.getId();
    }

    @Override
    public void updatePaymentStatus(Long paymentId, PaymentStatus status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found: " + paymentId));

        payment.setStatus(status);
        payment.setUpdatedAt(LocalDateTime.now());
        ;

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
