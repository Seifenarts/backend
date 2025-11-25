package de.seifenarts.domain.dto.order_dto.request_dto;

import de.seifenarts.domain.entity.Customer;
import de.seifenarts.domain.entity.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class OrderRequestDto {

    @NotNull
    private Long customerId;

    @NotNull
    private BigDecimal totalPrice;

    private OrderStatus status = OrderStatus.CREATED;

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
        return "OrderRequestDto{" +
                "customerId=" + customerId +
                ", totalPrice=" + totalPrice +
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
