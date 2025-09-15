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

    @NotNull
    private Long id;

    @NotBlank
    private String title;

    private BigDecimal price;

    @NotNull
    private Size size;

    @NotNull
    private BigDecimal deliveryPrice;

    @NotBlank
    private String shortDescription;

    @NotBlank
    private String fullDescription;

    @NotBlank
    private String composition;

    @NotEmpty
    private Set<String> aromas = new HashSet<>();

    private List<String> imageUrls = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("Product: id - %d, title - %s, price - %.2f, size - %s, deliveryPrice - %.2f, shortDescription - %s, fullDescription - %s, composition - %s",
                id, title, price, size, deliveryPrice, shortDescription, fullDescription, composition);
    }
}
