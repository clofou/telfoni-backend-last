package org.bamappli.telfonibackendspring.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.Entity.Reparateur;
import org.bamappli.telfonibackendspring.Entity.Reparation;
import org.bamappli.telfonibackendspring.Entity.Utilisateur;
import org.bamappli.telfonibackendspring.Repository.ReparationRepo;
import org.bamappli.telfonibackendspring.Utils.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReparationService implements CrudService<Long, Reparation>{

    private final ReparationRepo reparationRepo;
    private final UserService userService;

    @Override
    public Reparation creer(Reparation reparation) {
        Utilisateur utilisateur = userService.getCurrentUser();
        reparation.setReparateur((Reparateur) utilisateur);
        return reparationRepo.save(reparation);
    }

    @Override
    public Reparation modifer(Long id, Reparation reparation) {
        Utilisateur user = userService.getCurrentUser();
        Optional<Reparation> reparation1 = reparationRepo.findById(id);

        if (reparation1.isPresent() && (Objects.equals(user.getRole().getNom(), "ADMIN") || Objects.equals(user.getRole().getNom(), "REPARATEUR"))){
            Reparation reparationExist = reparation1.get();
            if (reparation.getStatut() != null) reparationExist.setStatut(reparation.getStatut());
            if (reparation.getDescriptionProbleme() != null) reparationExist.setDescriptionProbleme(reparation.getDescriptionProbleme());
            return reparationRepo.save(reparationExist);
        }else{
            throw new IllegalArgumentException("La Reparation n'existe pas ou l'utilisateur connecte n'as pas le droit d'effectuer cette action");
        }
    }

    @Override
    public Optional<Reparation> trouver(Long id) {
        return reparationRepo.findById(id);
    }

    @Override
    public List<Reparation> recuperer() {
        return reparationRepo.findAll();
    }

    @Override
    public void supprimer(Long id) {
        Utilisateur user = userService.getCurrentUser();
        Optional<Reparation> reparation = reparationRepo.findById(id);

        if(reparation.isPresent()){
            if (Objects.equals(user.getRole().getNom(), "ADMIN") || Objects.equals(user.getRole().getNom(), "REPARATEUR")){
                reparationRepo.deleteById(id);
            }
        } else{
            throw new IllegalArgumentException("La Reparation mentionne n'existe pas");
        }
    }
}
