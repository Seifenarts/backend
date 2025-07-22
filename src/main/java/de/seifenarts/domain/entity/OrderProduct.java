package de.seifenarts.domain.entity;

import de.seifenarts.domain.composite_key.OrderProductId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "order_product")
public class OrderProduct {

    @EmbeddedId
    private OrderProductId id = new OrderProductId();

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    @Column(name = "quantity")
    private Long quantity;

    @NotNull
    @Digits(integer = 4, fraction = 2)
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Override
    public String toString() {
        return String.format("OrderProduct: quantity - %d, totalPrice - %.2f", quantity, totalPrice);
    }
}
