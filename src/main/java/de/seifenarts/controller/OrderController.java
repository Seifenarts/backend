package de.seifenarts.controller;

import de.seifenarts.domain.dto.order_dto.request_dto.OrderRequestDto;
import de.seifenarts.domain.dto.order_dto.response_dto.OrderResponseDto;
import de.seifenarts.service.interfaces.OrderService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Long createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return orderService.addNewOrder(orderRequestDto);
    }

    @PutMapping("{id}")
    public OrderResponseDto updateOrder(
            @PathVariable("id") Long orderId,
            @RequestBody OrderRequestDto dto
    ) {
        return orderService.updateOrder(orderId, dto);
    }

    @GetMapping("/{id}")
    public OrderResponseDto getOrderById(@PathVariable("id") Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @DeleteMapping("{id}")
    public void deleteOrder(@PathVariable("id") Long orderId) {
        orderService.deleteOrder(orderId);
    }
}
