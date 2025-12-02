package de.seifenarts.domain.dto.order_dto.request_dto;


import de.seifenarts.domain.dto.OrderProduct.request_order_product_dto.OrderProductRequest;
import de.seifenarts.domain.entity.DeliveryMethod;
import de.seifenarts.domain.entity.OrderStatus;
import jakarta.validation.constraints.NotBlank;
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
    private List<OrderProductRequest> products;

    @NotNull
    private Long customerId;

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

    private DeliveryMethod deliveryMethod;

    @Override
    public String toString() {
        return String.format("Order: customer - %s, firstName - %s, lastName - %s, zipCode - %s, street - %s, houseNumber - %s, city - %s, deliveryMethod - %s",
                customerId,
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
