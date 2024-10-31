package org.bamappli.telfonibackendspring.Mapper;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.DTO.ReparationDTO;
import org.bamappli.telfonibackendspring.Entity.Reparation;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@AllArgsConstructor
public class ReparationDTOMapper implements Function<Reparation, ReparationDTO> {

    private UserDTOMapper userDTOMapper;

    @Override
    public ReparationDTO apply(Reparation reparation) {
        return new ReparationDTO(reparation.getId(), reparation.getTelephone().getTitre(), reparation.getDescriptionProbleme(), reparation.getDatePriseEnCharge(), userDTOMapper.apply(reparation.getTelephone().getUtilisateur()));
    }
}
