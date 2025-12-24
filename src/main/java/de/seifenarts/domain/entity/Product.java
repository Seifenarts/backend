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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "title")
    private String title;


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
    @Column(name = "short_description", length = 80, nullable = false)
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
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Image> images = new HashSet<>();

    @Column(name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active = true;

    @NotNull
    @Column(name = "quantity")
    private Integer quantity = 0;

    @Column(name = "stock_status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean stockStatus = true;

    @Override
    public String toString() {
        return String.format(
                "Product: id=%d, title=%s, price=%.2f, size=%s, quantity=%d, stock=%s",
                id, title, price, size, quantity, stockStatus ? "In stock" : "Out of stock"
        );
    }
}
