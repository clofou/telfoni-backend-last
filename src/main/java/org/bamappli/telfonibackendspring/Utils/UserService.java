package org.bamappli.telfonibackendspring.Utils;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.Entity.Utilisateur;
import org.bamappli.telfonibackendspring.Repository.UtilisateurRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    private UtilisateurRepo utilisateurRepo;

    public Utilisateur getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return utilisateurRepo.findByEmail(authentication.getName());
    }
}
