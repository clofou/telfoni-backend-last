package org.bamappli.telfonibackendspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BrandDTO {
    private Long id;
    private String image;
    private String nom;
}
