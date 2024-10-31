package org.bamappli.telfonibackendspring.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.Entity.Admin;
import org.bamappli.telfonibackendspring.Entity.Modele;
import org.bamappli.telfonibackendspring.Entity.Utilisateur;
import org.bamappli.telfonibackendspring.Repository.ModeleRepo;
import org.bamappli.telfonibackendspring.Utils.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ModeleService implements CrudService<Long, Modele> {

    private final ModeleRepo modeleRepo;
    private final UserService userService;

    @Override
    public Modele creer(Modele modele) {
        Utilisateur user = userService.getCurrentUser();
        modele.setAdmin((Admin) user);
        return modeleRepo.save(modele);
    }

    @Override
    public Modele modifer(Long id, Modele modele) {
        Utilisateur user = userService.getCurrentUser();
        Optional<Modele> modele1 = modeleRepo.findById(id);

        if (modele1.isPresent() && Objects.equals(user.getRole().getNom(), "ADMIN")){
            Modele modeleExist = modele1.get();
            if (modele.getNom() != null) modeleExist.setNom(modele.getNom());
            if (modele.getBrand() != null) modeleExist.setBrand(modele.getBrand());
            return modeleRepo.save(modeleExist);
        }else{
            throw new IllegalArgumentException("Le modele n'existe pas ou l'utilisateur connecte n'as pas le droit d'effectuer cette action");
        }
    }

    @Override
    public Optional<Modele> trouver(Long id) {
        return modeleRepo.findById(id);
    }

    @Override
    public List<Modele> recuperer() {
        return modeleRepo.findAll();
    }

    @Override
    public void supprimer(Long id) {
        Utilisateur user = userService.getCurrentUser();
        Optional<Modele> modele = modeleRepo.findById(id);

        if(modele.isPresent()){
            if (Objects.equals(user.getRole().getNom(), "ADMIN")){
                modeleRepo.deleteById(id);
            }
        } else{
            throw new IllegalArgumentException("Le modele mentionne n'existe pas");
        }
    }
}
