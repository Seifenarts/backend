package de.seifenarts.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_main")
    private boolean isMain;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public String toString() {
        return String.format("Image: id - %d, iamgeUrl - %s, isMain - %s", id, imageUrl, isMain);
    }
}
