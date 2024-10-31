package org.bamappli.telfonibackendspring.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.DTO.*;
import org.bamappli.telfonibackendspring.Entity.*;
import org.bamappli.telfonibackendspring.Enum.AnnonceStatut;
import org.bamappli.telfonibackendspring.Mapper.*;
import org.bamappli.telfonibackendspring.Repository.*;
import org.bamappli.telfonibackendspring.Services.*;
import org.bamappli.telfonibackendspring.Utils.FileOperation;
import org.bamappli.telfonibackendspring.Utils.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "client")
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class ClientC {
    private final ClientRepo clientRepo;
    private final TelephoneService telephoneService;
    private final TelephoneRepo telephoneRepo;
    private AnnonceRepo annonceRepo;
    private AnnonceDTOMapper annonceDTOMapper;
    private ClientServiceC clientServiceC;
    private BrandRepo brandRepo;
    private ReparationRepo reparationRepo;
    private BrandDTOMapper brandDTOMapper;
    private ReparationDTOMapper reparationDTOMapper;
    private PromotionRepo promotionRepo;
    private PromotionDTOMapper promotionDTOMapper;
    private UserDTOMapper userDTOMapper;
    private UserService userService;
    private ClientService clientService;
    private AnnonceService annonceService;
    private HistoriqueWalletRechargeService historiqueWalletRechargeService;
    private PasswordEncoder passwordEncoder;
    private MessageService messageService;

    @GetMapping(path = "annonces/en_vente")
    Stream<AnnonceDTO> listeAnnonce(){
        return annonceRepo.findAllByStatut(AnnonceStatut.EN_VENTE).stream().map(annonceDTOMapper);
    }

    @GetMapping(path = "annonces/tout")
    Stream<AnnonceDTO> listeAnnonceAll(){
        Client client =(Client) userService.getCurrentUser();
        return annonceRepo.findAllMy(client).stream().map(annonceDTOMapper);
    }

    @DeleteMapping(path = "annonce/{id}")
    public void supprimerAnnonce(@PathVariable Long id) throws IOException {
        Annonce annonce = annonceRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Annonce not found with ID: " + id));
        // Supprimer l'annonce de la base de données
        annonceRepo.deleteById(id);
        // Supprimer les fichiers de photos
        for (Photos photo : annonce.getPhone().getPhotosList()) {
            FileOperation.deleteFile("src/main/resources/static/annonce/"+photo.getNom()); // Implémentez cette méthode pour supprimer le fichier du système
        }


    }

    @PostMapping(path = "annonce/creer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Annonce creerAnnonce(@RequestPart("telephone") String telephoneJson,
                                @RequestPart("photos") List<MultipartFile> photos) throws JsonProcessingException {
        List<Photos> photosList = new ArrayList<>();
        photos.forEach(photo -> {
            try {
                String photoUrl = FileOperation.uploadFile(photo, "src/main/resources/static/annonce");
                photosList.add(new Photos(null, photoUrl));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Telephone telephone = new ObjectMapper().readValue(telephoneJson, Telephone.class);

        telephone.setPhotosList(photosList);
        telephoneService.creer(telephone);
        Annonce annonce1 = new Annonce();
        annonce1.setPhone(telephone);
        return annonceService.creer(annonce1);
    }

    @PostMapping(path = "annonce/modifier", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Telephone modifierAnnonce(@RequestPart("telephone") String telephoneJson,
                                   @RequestPart(value = "newPhotos", required = false) List<MultipartFile> newPhotos,
                                     @RequestPart(value = "existingPhotosIds", required = false) String existingPhotosIdsJson, @RequestPart(value = "id", required = false) String idJson
                                  ) throws JsonProcessingException {
        Long id = idJson.isEmpty() ? null : Long.parseLong(idJson);
        System.out.println("KILLLO " + id);
        Telephone telephone1 = new ObjectMapper().readValue(telephoneJson, Telephone.class);
        assert id != null;
        Optional<Telephone> telephone2 = telephoneRepo.findById(id);
        List<String> existingPhotosIds = Stream.of(existingPhotosIdsJson.split(",")).map(String::trim).collect(Collectors.toList());
        System.out.println(existingPhotosIds);
        System.out.println(newPhotos);
        System.out.println(existingPhotosIds);
        System.out.println(id);
        // Récupération des photos existantes
        List<Photos> existingPhotos = telephone2.get().getPhotosList().stream()
                .filter(photo -> existingPhotosIds.contains(photo.getNom()))
                .collect(Collectors.toList());
        System.out.println("=========================" + newPhotos);
        // Ajout des nouvelles photos

        if(newPhotos != null){
            newPhotos.forEach(photo -> {
                System.out.println("OOO" + photo);
                try {
                    String photoUrl = FileOperation.uploadFile(photo, "src/main/resources/static/annonce");
                    existingPhotos.add(new Photos(null, photoUrl));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }


        System.out.println("IIIIIIIIII" + existingPhotos);

        telephone2.get().setPhotosList(existingPhotos);
        System.out.println(telephone2.get().getPhotosList());
        telephone2.get().setPrix(telephone1.getPrix());
        System.out.println(telephone2.get().getPrix());
        telephone2.get().setBrand(telephone1.getBrand());
        System.out.println(telephone2.get().getBrand());
        telephone2.get().setMemoire(telephone1.getMemoire());
        System.out.println(telephone2.get().getMemoire());
        telephone2.get().setTagsList(telephone1.getTagsList());
        System.out.println(telephone2.get().getTagsList());
        telephone2.get().setTitre(telephone1.getTitre());
        telephone2.get().setDescription(telephone1.getDescription());
        Annonce annonce = annonceRepo.findAnnonceByPhoneId(id);
        annonce.setStatut(AnnonceStatut.EN_ATTENTE_DE_VALIDATION);
        annonceRepo.save(annonce);


        return telephoneService.creer(telephone2.get());
    }


    @PatchMapping(path = "annonces/liked/{id}")
    void likeAndUnlike(@PathVariable("id") Long id){
        System.out.println("idjjjjj");
        clientServiceC.likerAnnonce(id);
    }

    @GetMapping(path = "brands/liste")
    Stream<BrandDTO> listeBrands(){
        return brandRepo.findAll().stream().map(brandDTOMapper);
    }

    @GetMapping(path = "promotions/liste")
    Stream<PromotionDTO> listePromotions(){
        return promotionRepo.findAll().stream().map(promotionDTOMapper);
    }

    @GetMapping(path = "user/current")
    public UtilisateurDTO getCurrentUserInfo(){
        return userDTOMapper.apply(userService.getCurrentUser());
    }

    @PatchMapping(path = "user/modify/{id}")
    public Client modifierUserInfo(@PathVariable Long id,@RequestBody Client client){
        return clientService.modifer(id, client);
    }

    @GetMapping(path = "wallet/info")
    public InfoWalletDTO infoWallet(){
        Client client =(Client) userService.getCurrentUser();
        InfoWalletDTO infoWalletDTO = new InfoWalletDTO();
        infoWalletDTO.setSolde(client.getCompte().getSolde());
        infoWalletDTO.setSoldeBloque(client.getCompte().getMontantBloque());
        infoWalletDTO.setHistorique(historiqueWalletRechargeService.tt(client.getCompte().getId()));
        return infoWalletDTO;
    }

    @PostMapping(path = "wallet/password/change")
    public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO){
        Client client = (Client) userService.getCurrentUser();
        // Vérifier si l'ancien mot de passe est correct
        String oldPassword = passwordChangeDTO.getOldPassword();
        String storedPassword = client.getCompte().getCodeSecret();

        // Debugging: affichage des valeurs
        System.out.println("Old Password Attempted: " + oldPassword);
        System.out.println("Stored Password: " + storedPassword);

        if (passwordEncoder.matches(oldPassword, storedPassword)) {
            // Si oui, changer le mot de passe
            client.getCompte().setCodeSecret(passwordEncoder.encode(passwordChangeDTO.getNewPassword()));
        } else {
            System.out.println("Wrong old password");
            throw new IllegalArgumentException("Wrong old password");
        }

        clientRepo.save(client);
    }


    @PostMapping(path = "reparation/demande")
    public Reparation demandeDeReparation(@RequestBody Reparation reparation){
        telephoneService.creer(reparation.getTelephone());
        return reparationRepo.save(reparation);
    }

    @GetMapping(path = "reparation/info")
    public Stream<ReparationDTO> recupererListeReparation(){
        Client client =(Client) userService.getCurrentUser();
        return reparationRepo.findByUser(client).stream().map(reparationDTOMapper);
    }



    @GetMapping(path = "brand/liste")
    public Stream<BrandDTO> listeBrand(){
        System.out.println("============================");
        return brandRepo.findAll().stream().map(brandDTOMapper);
    }

    @PostMapping(path = "test")
    public Message creer(@RequestBody MessageRequestDTO message) throws IOException, ExecutionException, InterruptedException {
        return messageService.creer1(message);
    }
}
