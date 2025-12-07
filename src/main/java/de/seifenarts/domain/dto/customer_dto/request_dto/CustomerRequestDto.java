package de.seifenarts.domain.dto.customer_dto.request_dto;

import de.seifenarts.domain.entity.DeliveryMethod;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CustomerRequestDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String street;

    @NotBlank
    private String houseNumber;

    @NotBlank
    private String zipCode;

    @NotBlank
    private String city;

    @NotBlank
    private String country;

    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    private DeliveryMethod deliveryMethod;

    @Override
    public String toString() {
        return String.format("Customer: firstName - %s, lastName - %s, zipCode - %s, street - %s, houseNumber - %s, city - %s, deliveryMethod - %s",
                firstName,
                lastName,
                zipCode,
                street,
                houseNumber,
                city,
                deliveryMethod);
    }
}
