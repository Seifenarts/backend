package de.seifenarts.controller;

import de.seifenarts.domain.dto.order_dto.request_dto.OrderRequestDto;
import de.seifenarts.domain.dto.payment_dto.request_dto.PaymentRequestDto;
import de.seifenarts.domain.dto.payment_dto.respons_dto.PaymentResponseDto;
import de.seifenarts.repository.PaymentRepository;
import de.seifenarts.service.PaymentServiceImpl;
import de.seifenarts.service.interfaces.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentResponseDto createPayment(@RequestBody PaymentRequestDto dto) {

        return paymentService.createPayment(dto);
    }

    @GetMapping("/{paymentId}")
    public PaymentResponseDto getPaymentById(@PathVariable Long paymentId) {

        return paymentService.getPaymentById(paymentId);
    }




}
