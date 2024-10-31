package org.bamappli.telfonibackendspring.Repository;

import org.bamappli.telfonibackendspring.Entity.Annonce;
import org.bamappli.telfonibackendspring.Entity.Utilisateur;
import org.bamappli.telfonibackendspring.Enum.AnnonceStatut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AnnonceRepo extends JpaRepository<Annonce, Long> {

    @Query("select a from Annonce a where a.statut = :statut")
    List<Annonce> findAllByStatut(@Param("statut") AnnonceStatut statut);

    @Query("select a from Annonce a where a.utilisateur = :user")
    List<Annonce> findAllMy(@Param("user") Utilisateur utilisateur);

    Annonce findAnnonceByPhoneId(Long id);
}
