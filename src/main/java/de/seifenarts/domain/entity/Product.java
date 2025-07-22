package de.seifenarts.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "price")
    private BigDecimal price;

    @NotNull
    @Column(name = "size")
    @Enumerated(EnumType.STRING)
    private Size size;

    @NotNull
    @Column(name = "delivery_price")
    private BigDecimal deliveryPrice;

    @NotBlank
    @Column(name = "short_description")
    private String shortDescription;

    @NotBlank
    @Column(name = "full_description")
    private String fullDescription;

    @NotBlank
    @Column(name = "composition")
    private String composition;

    @NotNull
    @ManyToMany
    @JoinTable(
            name = "product_aroma",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "aroma_id")
    )
    private Set<Aroma> aromas = new HashSet<>();

    @NotNull
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> images = new HashSet<>();

    @Override
    public String toString() {
        return String.format("Product: id - %d, title - %s, price - %.2f, size - %s, deliveryPrice - %.2f, shortDescription - %s, fullDescription - %s, composition - %s", id, title, price, size, deliveryPrice, shortDescription, fullDescription, composition);
    }
}
