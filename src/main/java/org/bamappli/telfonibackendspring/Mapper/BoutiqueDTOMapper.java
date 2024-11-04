package org.bamappli.telfonibackendspring.Mapper;


import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.DTO.AnnonceDTO;
import org.bamappli.telfonibackendspring.DTO.BoutiqueResponseDTO;
import org.bamappli.telfonibackendspring.Entity.Boutique;
import org.bamappli.telfonibackendspring.Repository.AnnonceRepo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;


@Component
@AllArgsConstructor
public class BoutiqueDTOMapper implements Function<Boutique, BoutiqueResponseDTO> {

    private final AnnonceDTOMapper annonceDTOMapper;
    private final AnnonceRepo annonceRepo;

    @Override
    public BoutiqueResponseDTO apply(Boutique boutique) {
        Stream<AnnonceDTO> annonceDTOList = annonceRepo.findAllMy(boutique).stream().map(annonceDTOMapper);
        return new BoutiqueResponseDTO(boutique.getId(), boutique.getNom(), boutique.getEmail(), boutique.getNumeroDeTelephone(), boutique.getGrade(), boutique.getCompte().getSolde(), boutique.getPhotoUrl(), boutique.getDescription(), boutique.getRating(), annonceDTOList );
    }
}
