package de.seifenarts.service.interfaces;

import de.seifenarts.domain.dto.order_dto.request_dto.OrderRequestDto;
import de.seifenarts.domain.dto.order_dto.response_dto.OrderResponseDto;

public interface OrderService {

    Long addNewOrder(OrderRequestDto orderRequestDto);

//OrderResponseDto updateOrder (Long orderId, OrderRequestDto orderRequestDto);

    void deleteOrder(Long orderId);

    OrderResponseDto getOrderById(Long orderId);

    void markOrderPaid(Long orderId, Long paymentId);

    void markOrderCancelled(Long orderId);

}
