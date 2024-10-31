package org.bamappli.telfonibackendspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthentificationDTO {
    private String email;
    private String motDePasse;
}
