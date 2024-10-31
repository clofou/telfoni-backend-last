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
public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "acheteur")
    private Utilisateur acheteur;

    @ManyToOne
    @JoinColumn(name = "vendeur")
    private Utilisateur vendeur;

    @ManyToOne
    private Annonce annonce;

}
