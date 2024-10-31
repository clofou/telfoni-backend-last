package org.bamappli.telfonibackendspring.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.Entity.Annonce;
import org.bamappli.telfonibackendspring.Entity.Client;
import org.bamappli.telfonibackendspring.Repository.AnnonceRepo;
import org.bamappli.telfonibackendspring.Repository.ClientRepo;
import org.bamappli.telfonibackendspring.Utils.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientServiceC {
    private final ClientRepo clientRepo;
    private final AnnonceRepo annonceRepo;
    private final UserService userService;

    void likerAnnonce(Long idAnnonce){
        Client currentUser = (Client) userService.getCurrentUser();
        Optional<Annonce> annonce = annonceRepo.findById(idAnnonce);
        if(annonce.isPresent()){
            System.out.println("Annonce liked!");
            if (!currentUser.getPanier().getAnnonceList().contains(annonce.get())){
                currentUser.getPanier().getAnnonceList().add(annonce.get());
            }else{
                currentUser.getPanier().getAnnonceList().remove(annonce.get());
            }
        }

        clientRepo.save(currentUser);
    }
}
