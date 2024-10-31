package org.bamappli.telfonibackendspring.Mapper;


import org.bamappli.telfonibackendspring.DTO.BrandDTO;
import org.bamappli.telfonibackendspring.Entity.Brand;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BrandDTOMapper implements Function<Brand, BrandDTO> {
    @Override
    public BrandDTO apply(Brand brand) {
        return new BrandDTO(brand.getId(), brand.getImageUrl(), brand.getNom());
    }
}
