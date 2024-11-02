package org.bamappli.telfonibackendspring.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.DTO.*;
import org.bamappli.telfonibackendspring.Entity.Boutique;
import org.bamappli.telfonibackendspring.Entity.Brand;
import org.bamappli.telfonibackendspring.Entity.Controller;
import org.bamappli.telfonibackendspring.Entity.Reparateur;
import org.bamappli.telfonibackendspring.Enum.TransactionStatut;
import org.bamappli.telfonibackendspring.Mapper.CommandeDTOMapper;
import org.bamappli.telfonibackendspring.Repository.CommandeRepo;
import org.bamappli.telfonibackendspring.Repository.ControllerRepo;
import org.bamappli.telfonibackendspring.Repository.TransactionRepo;
import org.bamappli.telfonibackendspring.Services.BoutiqueService;
import org.bamappli.telfonibackendspring.Services.BrandService;
import org.bamappli.telfonibackendspring.Services.ControllerService;
import org.bamappli.telfonibackendspring.Services.ReparateurService;
import org.springframework.web.bind.annotation.*;

import javax.naming.ldap.Control;
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
    private BoutiqueService boutiqueService;
    private ReparateurService reparateurService;
    private ControllerService controllerService;
    private ControllerRepo controllerRepo;

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

    @GetMapping(path = "/boutique/liste")
    public List<BoutiqueModel> getAllBoutiquesOrderedByVentes(){
        return adminCService.getAllBoutiquesOrderedByVentes();
    }

    @PostMapping(path = "boutique/ajout")
    public Boutique creerBoutique(@RequestBody Boutique boutique){
        return boutiqueService.creer(boutique);
    }

    @GetMapping(path = "/reparateur/liste")
    public List<ReparateurModel> getAllReparateurOrderedByVentes(){
        return adminCService.getAllReparateurOrderedByVentes();
    }

    @PostMapping(path = "reparateur/ajout")
    public Reparateur creerReparateur(@RequestBody Reparateur reparateur){
        return reparateurService.creer(reparateur);
    }

    @GetMapping(path = "/controller/liste")
    public List<Controller> getAllControllerOrderedByVentes(){
        return controllerRepo.findAll();
    }

    @PostMapping(path = "controller/ajout")
    public Controller creerController(@RequestBody Controller controller){
        return controllerService.creer(controller);
    }
}
