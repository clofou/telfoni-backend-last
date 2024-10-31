package org.bamappli.telfonibackendspring.Repository;


import org.bamappli.telfonibackendspring.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepo extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByEmail(String email);
}