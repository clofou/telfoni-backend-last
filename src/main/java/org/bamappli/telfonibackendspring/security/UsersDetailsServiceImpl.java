package org.bamappli.telfonibackendspring.security;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.Entity.Utilisateur;
import org.bamappli.telfonibackendspring.Repository.UtilisateurRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsersDetailsServiceImpl implements UserDetailsService {

    private UtilisateurRepo utilisateurRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepo.findByEmail(username);
        if (utilisateur == null) {
            throw new UsernameNotFoundException(username);
        }

        return User.withUsername(utilisateur.getEmail()).password(utilisateur.getMotDePasse()).roles(utilisateur.getRole().getNom()).build();
    }
}
