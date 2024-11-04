package org.bamappli.telfonibackendspring.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.DTO.AnnonceDTO;
import org.bamappli.telfonibackendspring.DTO.NotificationRequest;
import org.bamappli.telfonibackendspring.Entity.Annonce;
import org.bamappli.telfonibackendspring.Entity.Client;
import org.bamappli.telfonibackendspring.Entity.Controller;
import org.bamappli.telfonibackendspring.Enum.AnnonceStatut;
import org.bamappli.telfonibackendspring.Mapper.AnnonceDTOMapper;
import org.bamappli.telfonibackendspring.Mapper.AnnonceDTOMapperC;
import org.bamappli.telfonibackendspring.Repository.AnnonceRepo;
import org.bamappli.telfonibackendspring.Services.ControllerService;
import org.bamappli.telfonibackendspring.Services.FCMService;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "controller")
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class ControllerC {

    private final ControllerService controllerService;
    private final AnnonceRepo annonceRepo;
    private final AnnonceDTOMapperC annonceDTOMapper;
    private final FCMService fcmService;

    @PatchMapping(path = "user/modify/{id}")
    public Controller modifierUserInfo(@PathVariable Long id, @RequestBody Controller controller){
        return controllerService.modifer(id, controller);
    }

    @GetMapping(path = "annonces/tout")
    Stream<AnnonceDTO> listeAnnonceAll(){
        return annonceRepo.findAll().stream().map(annonceDTOMapper);
    }

    @PatchMapping(path = "annonce/validation/{id}/{statut}")
    void validerOuRejeter(@PathVariable Long statut, @PathVariable Long id) throws ExecutionException, InterruptedException {
        Annonce annonce = annonceRepo.findById(id).get();
        if (statut == 0){
            annonce.setStatut(AnnonceStatut.REJETER);
        }else if (statut == 1){
            annonce.setStatut(AnnonceStatut.EN_VENTE);
        }
        annonceRepo.save(annonce);
        NotificationRequest notificationRequest = new NotificationRequest("Annonce "+ annonce.getPhone().getTitre(), annonce.getStatut().toString(), "Annonce", annonce.getUtilisateur().getFcmToken());
        fcmService.sendMessageToToken(notificationRequest);
    }
}
