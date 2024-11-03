package org.bamappli.telfonibackendspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCard {
    private double amount; // Montant à payer
    private String description; // Description de l'offre
    private String paymentUrl; // Lien pour procéder au paiement

    // Getters et setters
}
