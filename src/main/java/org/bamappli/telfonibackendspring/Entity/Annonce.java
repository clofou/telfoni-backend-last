package org.bamappli.telfonibackendspring.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bamappli.telfonibackendspring.Enum.AnnonceStatut;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private AnnonceStatut statut = AnnonceStatut.EN_ATTENTE_DE_VALIDATION;

    @ManyToOne(cascade = CascadeType.ALL)
    private Telephone phone;

    @ManyToOne
    private Utilisateur utilisateur;

}
