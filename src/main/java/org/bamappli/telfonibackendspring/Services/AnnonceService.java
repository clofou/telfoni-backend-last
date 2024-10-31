package org.bamappli.telfonibackendspring.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.Entity.Annonce;
import org.bamappli.telfonibackendspring.Entity.Utilisateur;
import org.bamappli.telfonibackendspring.Repository.AnnonceRepo;
import org.bamappli.telfonibackendspring.Utils.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AnnonceService implements CrudService<Long, Annonce>{

    private final AnnonceRepo annonceRepo;
    private final UserService userService;


    @Override
    public Annonce creer(Annonce annonce) {
        Utilisateur user = userService.getCurrentUser();
        annonce.setUtilisateur(user);
        return annonceRepo.save(annonce);
    }

    @Override
    public Annonce modifer(Long id, Annonce annonce) {
        Utilisateur user = userService.getCurrentUser();
        Optional<Annonce> annonce1 = annonceRepo.findById(id);

        if (annonce1.isPresent() && Objects.equals(user.getId(), annonce1.get().getUtilisateur().getId())){
            Annonce annonceExist = annonce1.get();
            if (annonce.getStatut() != null) annonceExist.setStatut(annonce.getStatut());
            return annonceRepo.save(annonceExist);
        }else{
            throw new IllegalArgumentException("L'annonce n'existe pas ou l'utilisateur connecte n'as pas le droit d'effectuer cette action");
        }
    }

    @Override
    public Optional<Annonce> trouver(Long id) {
        return annonceRepo.findById(id);
    }

    @Override
    public List<Annonce> recuperer() {
        return annonceRepo.findAll();
    }

    @Override
    public void supprimer(Long id) {
        Utilisateur user = userService.getCurrentUser();
        Optional<Annonce> annonce = annonceRepo.findById(id);

        if (annonce.isPresent()){
            Annonce annonce1 = annonce.get();

            if ((Objects.equals(user.getRole().getNom(), "ADMIN") || Objects.equals(user.getRole().getNom(), "CONTROLLER") || Objects.equals(user.getId(), annonce1.getUtilisateur().getId()))){
                annonceRepo.deleteById(id);
            }

        }else{
            throw new IllegalArgumentException("L'annonce mentionne n'existe pas");
        }

    }
}
