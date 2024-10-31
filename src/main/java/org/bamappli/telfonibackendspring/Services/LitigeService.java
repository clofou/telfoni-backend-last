package org.bamappli.telfonibackendspring.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.Entity.Client;
import org.bamappli.telfonibackendspring.Entity.Litige;
import org.bamappli.telfonibackendspring.Entity.Utilisateur;
import org.bamappli.telfonibackendspring.Repository.LitigeRepo;
import org.bamappli.telfonibackendspring.Utils.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class LitigeService implements CrudService<Long, Litige> {

    private final LitigeRepo litigeRepo;
    private final UserService userService;

    @Override
    public Litige creer(Litige litige) {
        Utilisateur user = userService.getCurrentUser();
        litige.setClient((Client) user);
        return litigeRepo.save(litige);
    }

    @Override
    public Litige modifer(Long id, Litige litige) {
        Utilisateur user = userService.getCurrentUser();
        Optional<Litige> litige1 = litigeRepo.findById(id);

        if (litige1.isPresent() && (Objects.equals(user.getRole().getNom(), "ADMIN") || Objects.equals(user.getId(), litige1.get().getClient().getId()))){
            Litige litigeExist = litige1.get();
            if (litige.getDescription() != null) litigeExist.setDescription(litige.getDescription());
            if (litige.getStatut() != null) litigeExist.setStatut(litige.getStatut());
            return litigeRepo.save(litigeExist);
        }else{
            throw new IllegalArgumentException("Le litige n'existe pas ou l'utilisateur connecte n'as pas le droit d'effectuer cette action");
        }
    }

    @Override
    public Optional<Litige> trouver(Long id) {
        return litigeRepo.findById(id);
    }

    @Override
    public List<Litige> recuperer() {
        return litigeRepo.findAll();
    }

    @Override
    public void supprimer(Long id) {
        Utilisateur user = userService.getCurrentUser();
        Optional<Litige> litige = litigeRepo.findById(id);
        if (litige.isPresent()){

            if ((Objects.equals(user.getRole().getNom(), "ADMIN") || Objects.equals(user.getId(), litige.get().getClient().getId()))){
                litigeRepo.deleteById(id);
            }
        }

    }
}
