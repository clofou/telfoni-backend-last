package org.bamappli.telfonibackendspring.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.Entity.Tags;
import org.bamappli.telfonibackendspring.Repository.TagsRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class TagsService implements CrudService<Long, Tags>{

    private TagsRepo tagsRepo;

    @Override
    public Tags creer(Tags tags) {
        return tagsRepo.save(tags);
    }

    @Override
    public Tags modifer(Long id, Tags tags) {
        Optional<Tags> tags1 = tagsRepo.findById(id);

        if (tags1.isPresent()){
            Tags tagsExist = tags1.get();
            if (tags.getNom() != null) tagsExist.setNom(tags.getNom());
            return tagsRepo.save(tagsExist);
        }else{
            throw new IllegalArgumentException("Le tags n'existe pas ou l'utilisateur connecte n'as pas le droit d'effectuer cette action");
        }
    }

    @Override
    public Optional<Tags> trouver(Long id) {
        return tagsRepo.findById(id);
    }

    @Override
    public List<Tags> recuperer() {
        return tagsRepo.findAll();
    }

    @Override
    public void supprimer(Long id) {
        tagsRepo.deleteById(id);
    }
}
