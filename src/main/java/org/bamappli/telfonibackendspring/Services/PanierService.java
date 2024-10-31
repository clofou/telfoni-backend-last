package org.bamappli.telfonibackendspring.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.Entity.Panier;
import org.bamappli.telfonibackendspring.Repository.PanierRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class PanierService implements CrudService<Long, Panier> {

    private final PanierRepo panierRepo;

    @Override
    public Panier creer(Panier panier) {
        return panierRepo.save(panier);
    }

    @Override
    public Panier modifer(Long id, Panier panier) {
        throw new IllegalArgumentException("Modif d'un panier impossible");
    }

    @Override
    public Optional<Panier> trouver(Long id) {
        return panierRepo.findById(id);
    }

    @Override
    public List<Panier> recuperer() {
        return panierRepo.findAll();
    }

    @Override
    public void supprimer(Long aLong) {
        throw new IllegalArgumentException("Impossible de supprimer un panier");
    }
}
