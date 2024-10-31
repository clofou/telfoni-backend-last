package org.bamappli.telfonibackendspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ReparationDTO {
    private Long id;
    private String titre;
    private String description;
    private Date datePriseEnCharge;
    private UtilisateurDTO reparateur;
}
