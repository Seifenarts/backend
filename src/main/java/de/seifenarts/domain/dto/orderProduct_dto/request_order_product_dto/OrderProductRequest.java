package de.seifenarts.domain.dto.orderProduct_dto.request_order_product_dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderProductRequest {
    @NotNull
    private Long id;

    @NotNull
    private Integer quantity;
}
