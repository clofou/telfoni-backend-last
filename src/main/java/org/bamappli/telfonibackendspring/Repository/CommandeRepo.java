package org.bamappli.telfonibackendspring.Repository;

import org.bamappli.telfonibackendspring.Entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommandeRepo extends JpaRepository<Commande, Long> {
    @Query("SELECT c from Commande c")
    List<Commande> findAllCommandes();
}
