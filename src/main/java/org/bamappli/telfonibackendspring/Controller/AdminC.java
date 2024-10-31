package org.bamappli.telfonibackendspring.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.DTO.CommandeDTO;
import org.bamappli.telfonibackendspring.DTO.VenteParMoisDTO;
import org.bamappli.telfonibackendspring.DTO.WalletDTO;
import org.bamappli.telfonibackendspring.Entity.Brand;
import org.bamappli.telfonibackendspring.Enum.TransactionStatut;
import org.bamappli.telfonibackendspring.Mapper.CommandeDTOMapper;
import org.bamappli.telfonibackendspring.Repository.CommandeRepo;
import org.bamappli.telfonibackendspring.Repository.TransactionRepo;
import org.bamappli.telfonibackendspring.Services.BrandService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "admin")
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class AdminC {

    private AdminCService adminCService;
    private BrandService brandService;
    private CommandeRepo commandeRepo;
    private CommandeDTOMapper commandeDTOMapper;
    private TransactionRepo transactionRepo;

    @PostMapping(path = "client/wallet/recharge")
    void rechargerCompte(@RequestBody WalletDTO wallet) {
        adminCService.rechargerCompte(wallet);
    }

    @PostMapping(path = "client/wallet/retrait")
    void retirerDuCompte(@RequestBody WalletDTO wallet) {
        adminCService.retirerArgent(wallet);
    }

    @PostMapping(path = "brand/ajout")
    public Brand creer(@RequestBody Brand brand){
        return brandService.creer(brand);
    }

    @GetMapping(path = "user/new")
    public Map<String, Object> getNewUsersStats(){
        return adminCService.getNewUsersStats();
    }

    @GetMapping(path = "commande/liste")
    public Stream<CommandeDTO> getCommande(){
        return commandeRepo.findAllCommandes().stream().map(commandeDTOMapper);
    }

    @GetMapping(path = "vente/total")
    public Double getTotalVente(){
        Double a = transactionRepo.findTotalMontantByStatut(TransactionStatut.PAYER);
        if (a == null) return 0.0;
        return a;
    }

    @GetMapping("/ventes-par-mois")
    public List<VenteParMoisDTO> getVentesParMois() {
        return transactionRepo.findMontantTotalParMois();
    }
}
