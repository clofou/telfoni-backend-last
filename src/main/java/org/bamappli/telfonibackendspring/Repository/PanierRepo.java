package org.bamappli.telfonibackendspring.Repository;



import org.bamappli.telfonibackendspring.Entity.Panier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PanierRepo extends JpaRepository<Panier, Long> {
    @Query("select count(p) from Panier p join p.annonceList a where a.id = :annonceId")
    Integer Lannonceapparaitdanscombiendepanier(@Param("annonceId") Long annonceId);
}