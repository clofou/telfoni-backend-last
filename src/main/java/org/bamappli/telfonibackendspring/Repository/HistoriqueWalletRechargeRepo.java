package org.bamappli.telfonibackendspring.Repository;


import org.bamappli.telfonibackendspring.Entity.HistoriqueWalletRecharge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoriqueWalletRechargeRepo extends JpaRepository<HistoriqueWalletRecharge, Long> {
    List<HistoriqueWalletRecharge> findByWalletIdOrderByDateDesc(Long id);
}
