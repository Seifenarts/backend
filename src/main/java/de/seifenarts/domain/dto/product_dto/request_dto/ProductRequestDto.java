package de.seifenarts.domain.dto.product_dto.request_dto;

import de.seifenarts.domain.entity.Size;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ProductRequestDto {

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
    private Set<Long> aromaIds = new HashSet<>();

    private List<MultipartFile> images = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("Product: title - %s, price - %.2f, size - %s, deliveryPrice - %.2f, shortDescription - %s, fullDescription - %s, composition - %s", title, price, size, deliveryPrice, shortDescription, fullDescription, composition);
    }
}
