package de.seifenarts.domain.dto.payment_dto.respons_dto;

import de.seifenarts.domain.entity.PaymentMethod;
import de.seifenarts.domain.entity.PaymentStatus;
import de.seifenarts.domain.entity.Provider;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class PaymentResponseDto {

    @NotNull
    private Long id;

    private Provider provider;

    private PaymentMethod paymentMethod;

    @NotNull
    private PaymentStatus status;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
