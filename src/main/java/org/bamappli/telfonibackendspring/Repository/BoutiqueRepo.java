package org.bamappli.telfonibackendspring.Repository;


import org.bamappli.telfonibackendspring.Entity.Boutique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoutiqueRepo extends JpaRepository<Boutique, Long> {
    @Query("SELECT b FROM Boutique b JOIN Transaction t ON t.phone.utilisateur.id = b.id GROUP BY b.id ORDER BY COUNT(t.id) DESC")
    List<Boutique> findAllBoutiquesOrderByVentes();
}
