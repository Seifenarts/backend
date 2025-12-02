package de.seifenarts.domain.dto.order_dto.response_dto;

import de.seifenarts.domain.entity.DeliveryMethod;
import de.seifenarts.domain.entity.OrderStatus;
import jakarta.validation.constraints.NotBlank;
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
public class OrderResponseDto {

    @NotNull
    private Long customerId;

    @NotNull
    private BigDecimal totalPrice;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private OrderStatus status;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String zipCode;

    @NotBlank
    private String street;

    @NotBlank
    private String houseNumber;

    @NotBlank
    private String city;

    private DeliveryMethod deliveryMethod;

    @Override
    public String toString() {
        return String.format("Order: customer - %s, totalPrice - - %.2f, firstName - %s, lastName - %s, zipCode - %s, street - %s, houseNumber - %s, city - %s, deliveryMethod - %s",
                customerId,
                totalPrice,
                firstName,
                lastName,
                zipCode,
                street,
                houseNumber,
                city,
                deliveryMethod
        );
    }
}
