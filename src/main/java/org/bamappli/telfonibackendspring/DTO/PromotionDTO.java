package org.bamappli.telfonibackendspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PromotionDTO {
    private Long id;
    private String pourcentage;
    private String titre;
    private String description;
    private String image;
}
