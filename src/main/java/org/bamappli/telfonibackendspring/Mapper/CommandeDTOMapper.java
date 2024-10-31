package org.bamappli.telfonibackendspring.Mapper;


import org.bamappli.telfonibackendspring.DTO.CommandeDTO;
import org.bamappli.telfonibackendspring.Entity.Commande;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CommandeDTOMapper implements Function<Commande, CommandeDTO> {
    @Override
    public CommandeDTO apply(Commande commande) {
        return new CommandeDTO(commande.getId(), commande.getTransaction().getPhone().getPhone().getTitre(), commande.getTransaction().getMontant(), commande.getTransaction().getAcheteur().getNom(), commande.getStatutAcheteur());
    }
}
