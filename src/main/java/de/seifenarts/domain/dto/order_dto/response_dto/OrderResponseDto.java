package de.seifenarts.domain.dto.order_dto.response_dto;

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

    @Override
    public String toString() {
        return "OrderResponseDto{" +
                "totalPrice=" + totalPrice +
                ", createdAt=" + createdAt +
                ", status=" + status +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
