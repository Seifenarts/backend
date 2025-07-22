package de.seifenarts.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private Long id;

    @Email(message = "Invalid e-mail format.")
    @NotBlank(message = "E-mail can't be empty.")
    @Column(name = "email")
    private String email;

    @NotNull
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders;

    @Override
    public String toString() {
        return String.format("Customer: id - %d, email - %s, orders - %s", id, email, orders);
    }

}
