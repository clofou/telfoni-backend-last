package org.bamappli.telfonibackendspring.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.Entity.Boutique;
import org.bamappli.telfonibackendspring.Entity.Promotion;
import org.bamappli.telfonibackendspring.Entity.Utilisateur;
import org.bamappli.telfonibackendspring.Repository.PromotionRepo;
import org.bamappli.telfonibackendspring.Utils.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PromotionService implements CrudService<Long, Promotion>{

    private final PromotionRepo promotionRepo;
    private final UserService userService;

    @Override
    public Promotion creer(Promotion promotion) {
        Utilisateur utilisateur = userService.getCurrentUser();
        promotion.setBoutique((Boutique) utilisateur);
        return promotionRepo.save(promotion);
    }

    @Override
    public Promotion modifer(Long id, Promotion promotion) {
        Utilisateur user = userService.getCurrentUser();
        Optional<Promotion> promotion1 = promotionRepo.findById(id);

        if (promotion1.isPresent() && Objects.equals(user.getId(), promotion1.get().getBoutique().getId())){
            Promotion promotionExist = promotion1.get();
            if (promotion.getPourcentageDeBaisse() != null) promotionExist.setPourcentageDeBaisse(promotion.getPourcentageDeBaisse());
            return promotionRepo.save(promotionExist);
        }else{
            throw new IllegalArgumentException("La Promotion n'existe pas ou l'utilisateur connecte n'as pas le droit d'effectuer cette action");
        }
    }

    @Override
    public Optional<Promotion> trouver(Long id) {
        return promotionRepo.findById(id);
    }

    @Override
    public List<Promotion> recuperer() {
        return promotionRepo.findAll();
    }

    @Override
    public void supprimer(Long id) {
        Utilisateur user = userService.getCurrentUser();
        Optional<Promotion> promotion = promotionRepo.findById(id);

        if(promotion.isPresent()){
            if (Objects.equals(user.getId(), promotion.get().getBoutique().getId())){
                promotionRepo.deleteById(id);
            }
        } else{
            throw new IllegalArgumentException("La Promotion mentionne n'existe pas");
        }
    }
}
