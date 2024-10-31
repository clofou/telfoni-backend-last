package org.bamappli.telfonibackendspring.Mapper;


import org.bamappli.telfonibackendspring.DTO.PromotionDTO;
import org.bamappli.telfonibackendspring.Entity.Promotion;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PromotionDTOMapper implements Function<Promotion, PromotionDTO> {
    @Override
    public PromotionDTO apply(Promotion promotion) {
        return new PromotionDTO(promotion.getId(), promotion.getPourcentageDeBaisse().toString(), promotion.getPhone().getTitre(), promotion.getPromotionDescription(), promotion.getImageUrl());
    }
}
