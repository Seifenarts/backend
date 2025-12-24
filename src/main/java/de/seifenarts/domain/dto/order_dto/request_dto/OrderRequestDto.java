package de.seifenarts.domain.dto.order_dto.request_dto;


import de.seifenarts.domain.dto.customer_dto.request_dto.CustomerRequestDto;
import de.seifenarts.domain.dto.orderProduct_dto.request_dto.OrderProductRequestDto;
import de.seifenarts.domain.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class OrderRequestDto {

    private Long userId;

    @NotNull
    private List<OrderProductRequestDto> products;

    @NotNull
    private CustomerRequestDto customer;

    private OrderStatus status = OrderStatus.CREATED;

    @Override
    public String toString() {
        return "OrderRequestDto{" +
                "userId=" + userId +
                ", products=" + products +
                ", customer=" + customer +
                ", status=" + status +
                '}';
    }

}
