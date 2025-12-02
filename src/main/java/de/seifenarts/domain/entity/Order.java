package de.seifenarts.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NotNull
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "zip_code")
    private String zipCode;

    @NotBlank
    @Column(name = "street")
    private String street;

    @NotBlank
    @Column(name = "house_number")
    private String houseNumber;

    @NotBlank
    @Column(name = "city")
    private String city;

    @Enumerated(EnumType.STRING)
    private DeliveryMethod deliveryMethod;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<OrderProduct> orderProducts = new HashSet<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Payment> payments = new HashSet<>();

    @Override
    public String toString() {
        String customerEmail = customer != null ? customer.getEmail() : "null";

        return String.format(
                "Order: id - %d, customer - %s, createdAt - %s, totalPrice - %.2f, firstName - %s, lastName - %s, zipCode - %s, street - %s, houseNumber - %s, city - %s, deliveryMethod - %s",
                id,
                customerEmail,
                createdAt,
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
