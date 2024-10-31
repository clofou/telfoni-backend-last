package org.bamappli.telfonibackendspring.Mapper;


import org.bamappli.telfonibackendspring.DTO.WalletHistoriqueDTO;
import org.bamappli.telfonibackendspring.Entity.HistoriqueWalletRecharge;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class HistoriqueDTOMapper implements Function<HistoriqueWalletRecharge, WalletHistoriqueDTO> {
    @Override
    public WalletHistoriqueDTO apply(HistoriqueWalletRecharge historiqueWalletRecharge) {
        String nom = null;
        if (historiqueWalletRecharge.getClient() != null){
            nom = historiqueWalletRecharge.getClient().getNom();
        }
        return new WalletHistoriqueDTO(nom, historiqueWalletRecharge.getTransactionType(), historiqueWalletRecharge.getDate(), historiqueWalletRecharge.getMontant());
    }
}
