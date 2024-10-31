package org.bamappli.telfonibackendspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bamappli.telfonibackendspring.Enum.CommandeStatut;

@AllArgsConstructor
@Getter
@Setter
public class CommandeDTO {
    private Long id;
    private String nom;
    private Double prix;
    private String acheteur;
    private CommandeStatut statut;

}
