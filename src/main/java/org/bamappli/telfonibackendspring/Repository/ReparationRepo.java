package org.bamappli.telfonibackendspring.Repository;


import org.bamappli.telfonibackendspring.Entity.Reparation;
import org.bamappli.telfonibackendspring.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface ReparationRepo extends JpaRepository<Reparation, Long> {
    @Query("select r from Reparation r where r.telephone.utilisateur = :user")
    List<Reparation> findByUser(@Param("user") Utilisateur utilisateur);

    @Query("select count(r) from Reparation r where r.reparateur.id=:id")
    Integer reparationNombre(@Param("id") Long reparateurId);
}