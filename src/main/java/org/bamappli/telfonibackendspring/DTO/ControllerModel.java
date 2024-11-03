package org.bamappli.telfonibackendspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bamappli.telfonibackendspring.Enum.Grade;

@Data
@AllArgsConstructor
public class ControllerModel {
    private Long id;
    private String nom;
    private double rating; // popularité calculée ou définie
    private String adresse;
    private String telephone;
    private String email;
    private Grade grade; // niveau de la boutique
    private int control; // nombre total de ventes
}
