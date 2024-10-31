package org.bamappli.telfonibackendspring.Mapper;


import org.bamappli.telfonibackendspring.DTO.BoutiqueResponseDTO;
import org.bamappli.telfonibackendspring.Entity.Boutique;
import org.springframework.stereotype.Component;

import java.util.function.Function;


@Component
public class BoutiqueDTOMapper implements Function<Boutique, BoutiqueResponseDTO> {
    @Override
    public BoutiqueResponseDTO apply(Boutique boutique) {
        return new BoutiqueResponseDTO(boutique.getId(), boutique.getNom(), boutique.getEmail(), boutique.getNumeroDeTelephone(), boutique.getGrade(), boutique.getCompte().getSolde());
    }
}
