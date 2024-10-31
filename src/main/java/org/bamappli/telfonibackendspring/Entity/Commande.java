package org.bamappli.telfonibackendspring.Entity;

import com.google.api.client.util.DateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bamappli.telfonibackendspring.Enum.CommandeStatut;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private CommandeStatut statutVendeur;
    private CommandeStatut statutAcheteur;
    private CommandeStatut statutController;
    private Date dateLivraison;

    @ManyToOne
    private Transaction transaction;

    @ManyToOne
    private Controller controller;
}
