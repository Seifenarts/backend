package de.seifenarts.domain.dto.product_dto.respons_dto;

import de.seifenarts.domain.entity.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ProductResponseDTO {

    private Long id;

    private String title;

    private BigDecimal price;

    private Size size;

    private BigDecimal deliveryPrice;

    private String shortDescription;

    private String fullDescription;

    private String composition;

    private Set<String> aromas = new HashSet<>();

    private List<String> imageUrls = new ArrayList<>();

    private Boolean active;

    private Integer quantity;

    private Boolean stockStatus;

    @Override
    public String toString() {
        return String.format("Product: id - %d, title - %s, price - %.2f, size - %s, deliveryPrice - %.2f, shortDescription - %s, fullDescription - %s, composition - %s, active - %b, quantity - %d, stockStatus - %b",
                id, title, price, size, deliveryPrice, shortDescription, fullDescription, composition, active, quantity, stockStatus);
    }
}
