package org.bamappli.telfonibackendspring.Repository;

import org.bamappli.telfonibackendspring.Entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommandeRepo extends JpaRepository<Commande, Long> {
    @Query("SELECT c from Commande c")
    List<Commande> findAllCommandes();

    @Query("select count(c) from Commande c where c.controller.id=:id")
    Integer controllTotal(@Param("id") Long controllerId);
}
