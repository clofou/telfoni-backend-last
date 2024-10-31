package org.bamappli.telfonibackendspring.Repository;

import org.bamappli.telfonibackendspring.Entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepo extends JpaRepository<Commande, Long> {

}
