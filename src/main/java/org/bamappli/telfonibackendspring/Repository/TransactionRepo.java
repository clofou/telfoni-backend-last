package org.bamappli.telfonibackendspring.Repository;


import org.bamappli.telfonibackendspring.DTO.VenteParMoisDTO;
import org.bamappli.telfonibackendspring.Entity.Transaction;
import org.bamappli.telfonibackendspring.Enum.TransactionStatut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    @Query("SELECT SUM(t.montant) FROM Transaction t WHERE t.statut = :statut")
    Double findTotalMontantByStatut(TransactionStatut statut);

    @Query("SELECT new org.bamappli.telfonibackendspring.DTO.VenteParMoisDTO(MONTH(t.dateDeTransaction), SUM(t.montant)) " +
            "FROM Transaction t " +
            "WHERE t.statut = 'PAYER' " + // Optionnel : Filtre pour les transactions payées
            "GROUP BY MONTH(t.dateDeTransaction)")
    List<VenteParMoisDTO> findMontantTotalParMois();
}