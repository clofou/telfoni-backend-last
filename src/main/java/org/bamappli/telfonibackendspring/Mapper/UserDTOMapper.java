package org.bamappli.telfonibackendspring.Mapper;

import org.bamappli.telfonibackendspring.DTO.UtilisateurDTO;
import org.bamappli.telfonibackendspring.Entity.Utilisateur;
import org.springframework.stereotype.Component;

import java.util.function.Function;


@Component
public class UserDTOMapper implements Function<Utilisateur, UtilisateurDTO> {
    @Override
    public UtilisateurDTO apply(Utilisateur utilisateur) {
        return new UtilisateurDTO(utilisateur.getId(), utilisateur.getNom(), utilisateur.getEmail(), utilisateur.getAdresse(), utilisateur.getRole().getNom(), utilisateur.getPhotoUrl(), utilisateur.getNumeroDeTelephone(), utilisateur.getCompte().getSolde().toString(), utilisateur.getCompte().getMontantBloque().toString(), utilisateur.getGrade(), utilisateur.getRating(), utilisateur.getTotalRating());
    }
}
