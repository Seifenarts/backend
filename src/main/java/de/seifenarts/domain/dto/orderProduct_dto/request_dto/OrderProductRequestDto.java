package de.seifenarts.domain.dto.orderProduct_dto.request_dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderProductRequestDto {
    @NotNull
    private Long id;

    @NotNull
    private Integer quantity;
}
