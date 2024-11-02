package org.bamappli.telfonibackendspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReparateurModel {
    private Long id;
    private String nom;
    private double rating; // popularité calculée ou définie
    private String adresse;
    private String telephone;
    private String email;
    private String grade; // niveau de la boutique
    private int reparations; // nombre total de ventes

}
