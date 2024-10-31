package org.bamappli.telfonibackendspring.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.Entity.Telephone;
import org.bamappli.telfonibackendspring.Entity.Utilisateur;
import org.bamappli.telfonibackendspring.Repository.TelephoneRepo;
import org.bamappli.telfonibackendspring.Utils.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TelephoneService implements CrudService<Long, Telephone>{

    private final TelephoneRepo telephoneRepo;
    private final UserService userService;

    @Override
    public Telephone creer(Telephone telephone) {
        Utilisateur utilisateur = userService.getCurrentUser();
        telephone.setUtilisateur(utilisateur);
        return telephoneRepo.save(telephone);
    }

    @Override
    public Telephone modifer(Long id, Telephone telephone) {
        Utilisateur user = userService.getCurrentUser();
        Optional<Telephone> telephone1 = telephoneRepo.findById(id);

        if (telephone1.isPresent() && Objects.equals(user.getRole().getNom(), "ADMIN")){
            Telephone telephoneExist = telephone1.get();
            if (telephone.getMemoire() != null) telephoneExist.setMemoire(telephone.getMemoire());
            if (telephone.getDescription() != null) telephoneExist.setDescription(telephone.getDescription());
            if (telephone.getTitre() != null) telephoneExist.setTitre(telephone.getTitre());
            if (telephone.getModele() != null) telephoneExist.setModele(telephone.getModele());
            if (telephone.getPrix() != null) telephoneExist.setPrix(telephone.getPrix());
            if (telephone.getBrand() != null) telephoneExist.setBrand(telephone.getBrand());
            if (telephone.getType() != null) telephoneExist.setType(telephone.getType());
            return telephoneRepo.save(telephoneExist);
        }else{
            throw new IllegalArgumentException("Le Telephone n'existe pas ou l'utilisateur connecte n'as pas le droit d'effectuer cette action");
        }
    }

    @Override
    public Optional<Telephone> trouver(Long id) {
        return telephoneRepo.findById(id);
    }

    @Override
    public List<Telephone> recuperer() {
        return telephoneRepo.findAll();
    }

    @Override
    public void supprimer(Long id) {
        telephoneRepo.deleteById(id);
    }
}
