package org.bamappli.telfonibackendspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bamappli.telfonibackendspring.Enum.AnnonceStatut;
import org.bamappli.telfonibackendspring.Enum.TelephoneType;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class AnnonceDTO {
    private Long id;
    private String nom;
    private List<String> photosList;
    private String prix;
    private boolean isLiked;
    private String description;
    private TelephoneType type;
    private String nomVendeur;
    private String boutiqueOuVendeur;
    private String statusVendeur;
    private int quantite;
    private int totalLikes;
    private Date dateDeCreation;
    private List<String> tagsList;
    private Long brandId;
    private AnnonceStatut annonceStatut;
}
