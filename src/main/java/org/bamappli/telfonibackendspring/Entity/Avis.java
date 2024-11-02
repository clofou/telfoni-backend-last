package org.bamappli.telfonibackendspring.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Avis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int noteVendeur;
    private int noteController;
    @Column(length = 2048)
    private String commentaire;

    @ManyToOne
    private Utilisateur utilisateurEvaluateur; // Celui qui donne l'avis

    @ManyToOne
    private Utilisateur vendeur; // Le vendeur noté

    @ManyToOne
    private Controller controller; // Le contrôleur noté

    @ManyToOne
    private Transaction transaction; // Transaction liée à l'avis

    private Date dateAvis = new Date(); // Date de l'avis
}
