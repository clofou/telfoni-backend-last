package org.bamappli.telfonibackendspring.Repository;


import org.bamappli.telfonibackendspring.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface UtilisateurRepo extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByEmail(String email);
    @Query("SELECT COUNT(u) FROM Utilisateur u WHERE u.dateCreation >= :startOfDay AND u.dateCreation < :endOfDay")
    Long countNewUsersToday(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);


    @Query(value = "SELECT COUNT(*) FROM utilisateur u WHERE u.date_creation = DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY)", nativeQuery = true)
    Long countNewUsersYesterday();

    // Liste des boutiques ordonnées par nombre de ventes
    @Query("SELECT b, COUNT(t) AS venteCount " +
            "FROM Boutique b " +
            "LEFT JOIN Transaction t ON t.phone.utilisateur = b " +
            "GROUP BY b " +
            "ORDER BY venteCount DESC")
    List<Object[]> findBoutiquesOrdreParVentes();

    // Liste des réparateurs ordonnés par nombre de ventes
    @Query("SELECT r, COUNT(t) AS venteCount " +
            "FROM Reparateur r " +
            "LEFT JOIN Transaction t ON t.phone.utilisateur = r " +
            "GROUP BY r " +
            "ORDER BY venteCount DESC")
    List<Object[]> findReparateursOrdreParVentes();

    // Liste des clients ordonnés par nombre de ventes
    @Query("SELECT c, COUNT(t) AS venteCount " +
            "FROM Client c " +
            "LEFT JOIN Transaction t ON t.acheteur = c " +
            "GROUP BY c " +
            "ORDER BY venteCount DESC")
    List<Object[]> findClientsOrdreParVentes();


}