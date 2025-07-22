package de.seifenarts.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "aroma")
public class Aroma {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    @NotBlank(message = "Title can't be blank.")
    private String title;

    @Override
    public String toString() {
        return String.format("Aroma: id - %d, title - %s", id, title);
    }

}
