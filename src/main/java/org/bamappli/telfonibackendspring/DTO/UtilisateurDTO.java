package org.bamappli.telfonibackendspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bamappli.telfonibackendspring.Enum.Grade;

@Data
@AllArgsConstructor
public class UtilisateurDTO {
    private Long id;
    private String nom;
    private String email;
    private String adresse;
    private String role;
    private String photoUrl;
    private String telephone;
    private String montantWallet;
    private String montantBloque;
    private Grade grade;
    private int rating;
    private int totalRating;

}
