package org.bamappli.telfonibackendspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoutiqueModel {
    private Long id;
    private String nom;
    private double popularite; // popularité calculée ou définie
    private String adresse;
    private String telephone;
    private String email;
    private String grade; // niveau de la boutique
    private boolean isLocked; // si le compte est verrouillé
    private int ventes; // nombre total de ventes


}

