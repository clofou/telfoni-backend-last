package org.bamappli.telfonibackendspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bamappli.telfonibackendspring.Enum.CommandeStatut;
import org.bamappli.telfonibackendspring.Enum.TransactionStatut;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class CommandeDTO {
    private Long id;
    private String nomAnnonce;
    private Double prix;
    private String acheteur;
    private TransactionStatut statutTransaction;
    private CommandeStatut statutVendeur;
    private CommandeStatut statutAcheteur;
    private CommandeStatut statutController;
    private Date dateLivraison;
    private String nomController;



}
