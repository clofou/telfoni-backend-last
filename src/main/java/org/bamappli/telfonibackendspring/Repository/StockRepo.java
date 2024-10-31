package org.bamappli.telfonibackendspring.Repository;

import org.bamappli.telfonibackendspring.Entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockRepo extends JpaRepository<Stock, Long> {

    @Query("select s.quantite from Stock s where s.telephone.id = :phoneId")
    Integer inStock(@Param("phoneId") Long phoneId);
}