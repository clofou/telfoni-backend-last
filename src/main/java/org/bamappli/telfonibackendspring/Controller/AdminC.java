package org.bamappli.telfonibackendspring.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.DTO.*;
import org.bamappli.telfonibackendspring.Entity.*;
import org.bamappli.telfonibackendspring.Enum.TransactionStatut;
import org.bamappli.telfonibackendspring.Mapper.*;
import org.bamappli.telfonibackendspring.Repository.*;
import org.bamappli.telfonibackendspring.Services.*;
import org.bamappli.telfonibackendspring.Utils.FileOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.ldap.Control;
import java.io.IOException;
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
    private ModeleService modeleService;
    private CommandeRepo commandeRepo;
    private AnnonceRepo annonceRepo;
    private CommandeDTOMapper commandeDTOMapper;
    private AnnonceDTOMapperC annonceDTOMapper;
    private TransactionRepo transactionRepo;
    private BoutiqueService boutiqueService;
    private ReparateurService reparateurService;
    private ControllerService controllerService;
    private ControllerRepo controllerRepo;
    private ControllerDTOMapper controllerDTOMapper;
    private ClientRepo clientRepo;
    private BrandDTOMapper brandDTOMapper;
    private ModeleDTOMapper modeleDTOMapper;

    @PostMapping(path = "client/wallet/recharge")
    void rechargerCompte(@RequestBody WalletDTO wallet) {
        adminCService.rechargerCompte(wallet);
    }

    @PostMapping(path = "client/wallet/retrait")
    void retirerDuCompte(@RequestBody WalletDTO wallet) {
        adminCService.retirerArgent(wallet);
    }

    // Brand CRUD
    @PostMapping(path = "brand/ajout")
    public Brand creerBrand(@RequestBody Brand brand){
        return brandService.creer(brand);
    }
    @GetMapping(path = "brand/liste")
    public Stream<BrandDTO> listeBrand(){
        return brandService.recuperer().stream().map(brandDTOMapper);
    }
    @DeleteMapping(path = "brand/supprimer/{id}")
    public void supprimerBrand(@PathVariable Long id){
       brandService.supprimer(id);
    }

    // Modele CRUD
    @PostMapping(path = "modele/ajout")
    public Modele creerModele(@RequestBody Modele modele){
        return modeleService.creer(modele);
    }
    @GetMapping(path = "modele/liste")
    public Stream<BrandDTO> listeModele(){
        return modeleService.recuperer().stream().map(modeleDTOMapper);
    }
    @DeleteMapping(path = "modele/supprimer/{id}")
    public void supprimerModele(@PathVariable Long id){
        modeleService.supprimer(id);
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
    public List<BoutiqueModel> getAllBoutiques(){
        return adminCService.getAllBoutiquesOrderedByVentes();
    }

    @PostMapping(path = "boutique/ajout")
    public Boutique creerBoutique(@RequestPart("boutique") String boutiqueJson,
                                  @RequestPart("photos") MultipartFile boutiqueImage) throws JsonProcessingException {
        Boutique boutique = new ObjectMapper().readValue(boutiqueJson, Boutique.class);
        try {
            String photoUrl = FileOperation.uploadFile(boutiqueImage, "src/main/resources/static/boutique");
            boutique.setPhotoUrl(photoUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return boutiqueService.creer(boutique);
    }

    @GetMapping(path = "/reparateur/liste")
    public List<ReparateurModel> getAllReparateur(){
        return adminCService.getAllReparateurOrderedByVentes();
    }

    @PostMapping(path = "reparateur/ajout")
    public Reparateur creerReparateur(@RequestPart("boutique") String reparateurJson,
                                      @RequestPart(value = "doc1", required = false) MultipartFile docOff1,
                                      @RequestPart(value = "doc2", required = false) MultipartFile docOff2,
                                      @RequestPart(value = "doc3", required = false) MultipartFile docOff3
                                      ) throws JsonProcessingException {
        Reparateur reparateur = new ObjectMapper().readValue(reparateurJson, Reparateur.class);
        try {
            String photoUrl = FileOperation.uploadFile(docOff1, "src/main/resources/static/reparateur");
            String photoUrl1 = FileOperation.uploadFile(docOff2, "src/main/resources/static/reparateur");
            String photoUrl2 = FileOperation.uploadFile(docOff3, "src/main/resources/static/reparateur");
            reparateur.setDocOff1(photoUrl);
            reparateur.setDocOff2(photoUrl1);
            reparateur.setDocOff3(photoUrl2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reparateurService.creer(reparateur);
    }

    @GetMapping(path = "/controller/liste")
    public Stream<ControllerModel> getAllController(){
        return controllerRepo.findAll().stream().map(controllerDTOMapper);
    }

    @PostMapping(path = "controller/ajout")
    public Controller creerController(@RequestPart("boutique") String controllerJson,
                                      @RequestPart(value = "doc1", required = false) MultipartFile docOff1,
                                      @RequestPart(value = "doc2", required = false) MultipartFile docOff2,
                                      @RequestPart(value = "doc3", required = false) MultipartFile docOff3
    ) throws JsonProcessingException {
        Controller controller = new ObjectMapper().readValue(controllerJson, Controller.class);
        try {
            String photoUrl = FileOperation.uploadFile(docOff1, "src/main/resources/static/controller");
            String photoUrl1 = FileOperation.uploadFile(docOff2, "src/main/resources/static/controller");
            String photoUrl2 = FileOperation.uploadFile(docOff3, "src/main/resources/static/controller");
            controller.setDocOff1(photoUrl);
            controller.setDocOff2(photoUrl1);
            controller.setDocOff3(photoUrl2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return controllerService.creer(controller);
    }

    @GetMapping(path = "clients/liste")
    public List<Client> recupClients(){
        return clientRepo.findAll();
    }

    @PostMapping(path = "controller/simple")
    public Controller creerCon(@RequestBody Controller controller){
        return controllerService.creer(controller);
    }

    @GetMapping(path = "annonces/tout")
    Stream<AnnonceDTO> listeAnnonceAll(){
        return annonceRepo.findAll().stream().map(annonceDTOMapper);
    }

}
