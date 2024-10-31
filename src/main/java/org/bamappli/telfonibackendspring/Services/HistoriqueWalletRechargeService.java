package org.bamappli.telfonibackendspring.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.DTO.WalletHistoriqueDTO;
import org.bamappli.telfonibackendspring.Entity.HistoriqueWalletRecharge;
import org.bamappli.telfonibackendspring.Mapper.HistoriqueDTOMapper;
import org.bamappli.telfonibackendspring.Repository.HistoriqueWalletRechargeRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HistoriqueWalletRechargeService implements CrudService<Long, HistoriqueWalletRecharge>{

    private final HistoriqueWalletRechargeRepo historiqueWalletRechargeRepo;
    private final HistoriqueDTOMapper historiqueDTOMapper;

    @Override
    public HistoriqueWalletRecharge creer(HistoriqueWalletRecharge historiqueWalletRecharge) {
        return historiqueWalletRechargeRepo.save(historiqueWalletRecharge);
    }

    @Override
    public HistoriqueWalletRecharge modifer(Long aLong, HistoriqueWalletRecharge historiqueWalletRecharge) {
        return null;
    }

    @Override
    public Optional<HistoriqueWalletRecharge> trouver(Long id) {
        return historiqueWalletRechargeRepo.findById(id);
    }

    @Override
    public List<HistoriqueWalletRecharge> recuperer() {
        return historiqueWalletRechargeRepo.findAll();
    }

    @Override
    public void supprimer(Long aLong) {

    }

    public List<WalletHistoriqueDTO> tt(Long id) {
        return historiqueWalletRechargeRepo.findByWalletIdOrderByDateDesc(id).stream().map(historiqueDTOMapper).toList();
    }
}
