package org.bamappli.telfonibackendspring.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double pourcentageDeBaisse;
    private String imageUrl;
    private String promotionDescription;

    @ManyToOne(cascade = CascadeType.ALL)
    private Telephone phone;

    @ManyToOne
    private Boutique boutique;
}
