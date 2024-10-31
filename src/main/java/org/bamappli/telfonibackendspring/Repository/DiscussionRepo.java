package org.bamappli.telfonibackendspring.Repository;


import org.bamappli.telfonibackendspring.Entity.Discussion;
import org.bamappli.telfonibackendspring.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscussionRepo extends JpaRepository<Discussion, Long> {
    List<Discussion> findByAcheteurOrVendeur(Utilisateur acheteur, Utilisateur vendeur);
}
