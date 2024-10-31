package org.bamappli.telfonibackendspring.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.Entity.Admin;
import org.bamappli.telfonibackendspring.Entity.Brand;
import org.bamappli.telfonibackendspring.Entity.Utilisateur;
import org.bamappli.telfonibackendspring.Repository.BrandRepo;
import org.bamappli.telfonibackendspring.Utils.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BrandService implements CrudService<Long, Brand>{

    private final BrandRepo brandRepo;
    private final UserService userService;

    @Override
    public Brand creer(Brand brand) {
        Utilisateur user = userService.getCurrentUser();
        brand.setAdmin((Admin) user);
        return brandRepo.save(brand);
    }

    @Override
    public Brand modifer(Long id, Brand brand) {
        Utilisateur user = userService.getCurrentUser();
        Optional<Brand> brand1 = brandRepo.findById(id);

        if (brand1.isPresent() && Objects.equals(user.getRole().getNom(), "ADMIN")){
            Brand brandExist = brand1.get();
            if (brand.getNom() != null) brandExist.setNom(brand.getNom());
            return brandRepo.save(brandExist);
        }else{
            throw new IllegalArgumentException("La marque n'existe pas ou l'utilisateur connecte n'as pas le droit d'effectuer cette action");
        }
    }

    @Override
    public Optional<Brand> trouver(Long id) {
        return brandRepo.findById(id);
    }

    @Override
    public List<Brand> recuperer() {
        return brandRepo.findAll();
    }

    @Override
    public void supprimer(Long id) {
        Utilisateur user = userService.getCurrentUser();
        Optional<Brand> brand = brandRepo.findById(id);

        if(brand.isPresent()){
            if (Objects.equals(user.getRole().getNom(), "ADMIN")){
                brandRepo.deleteById(id);
            }
        } else{
            throw new IllegalArgumentException("La Brand mentionne n'existe pas");
        }
    }
}
