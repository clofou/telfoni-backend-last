package org.bamappli.telfonibackendspring.DTO;

import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BoutiqueDTOClient {
    private String shopName;
    private String shopImageUrl;
    private String shopDescription;
    private String rating;
    private List<AnnonceDTO> annonces;
}
