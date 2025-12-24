package de.seifenarts.domain.dto.payment_dto.request_dto;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class PaymentRequestDto {

    @NotNull
    private Long orderId;
}
