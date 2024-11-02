package org.bamappli.telfonibackendspring.Repository;



import org.bamappli.telfonibackendspring.Entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface FavoriteRepo extends JpaRepository<Favorite, Long> {
    @Query("select count(p) from Favorite p join p.annonceList a where a.id = :annonceId")
    Integer Lannonceapparaitdanscombiendepanier(@Param("annonceId") Long annonceId);
}