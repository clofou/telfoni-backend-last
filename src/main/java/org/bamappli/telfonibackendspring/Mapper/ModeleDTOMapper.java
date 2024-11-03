package org.bamappli.telfonibackendspring.Mapper;


import org.bamappli.telfonibackendspring.DTO.BrandDTO;
import org.bamappli.telfonibackendspring.Entity.Brand;
import org.bamappli.telfonibackendspring.Entity.Modele;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ModeleDTOMapper implements Function<Modele, BrandDTO> {
    @Override
    public BrandDTO apply(Modele brand) {
        return new BrandDTO(brand.getId(), "", brand.getNom());
    }
}
