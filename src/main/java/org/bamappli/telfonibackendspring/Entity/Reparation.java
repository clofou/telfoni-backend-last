package org.bamappli.telfonibackendspring.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bamappli.telfonibackendspring.Enum.ReparationStatut;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reparation{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descriptionProbleme;
    private ReparationStatut statut;
    private Date datePriseEnCharge;

    @ManyToOne(cascade = CascadeType.ALL)
    private Telephone telephone;

    @ManyToOne
    private Reparateur reparateur;
}
