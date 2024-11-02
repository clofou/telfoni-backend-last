package org.bamappli.telfonibackendspring.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.Entity.Favorite;
import org.bamappli.telfonibackendspring.Repository.FavoriteRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class PanierService implements CrudService<Long, Favorite> {

    private final FavoriteRepo favoriteRepo;

    @Override
    public Favorite creer(Favorite favorite) {
        return favoriteRepo.save(favorite);
    }

    @Override
    public Favorite modifer(Long id, Favorite favorite) {
        throw new IllegalArgumentException("Modif d'un panier impossible");
    }

    @Override
    public Optional<Favorite> trouver(Long id) {
        return favoriteRepo.findById(id);
    }

    @Override
    public List<Favorite> recuperer() {
        return favoriteRepo.findAll();
    }

    @Override
    public void supprimer(Long aLong) {
        throw new IllegalArgumentException("Impossible de supprimer un panier");
    }
}
