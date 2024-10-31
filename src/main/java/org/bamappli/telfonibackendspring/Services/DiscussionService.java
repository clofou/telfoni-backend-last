package org.bamappli.telfonibackendspring.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.Entity.Annonce;
import org.bamappli.telfonibackendspring.Entity.Discussion;
import org.bamappli.telfonibackendspring.Entity.Utilisateur;
import org.bamappli.telfonibackendspring.Repository.AnnonceRepo;
import org.bamappli.telfonibackendspring.Repository.DiscussionRepo;
import org.bamappli.telfonibackendspring.Utils.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DiscussionService implements CrudService<Long, Discussion> {

    private final DiscussionRepo discussionRepo;
    private final UserService userService;
    private final AnnonceRepo annonceRepo;


    @Override
    public Discussion creer(Discussion discussion) {
        Utilisateur utilisateur = userService.getCurrentUser();
        discussion.setAcheteur(utilisateur);
        Optional<Annonce> optionalAnnonce = annonceRepo.findById(discussion.getAnnonce().getId());
        if (optionalAnnonce.isPresent()){
            discussion.setVendeur(optionalAnnonce.get().getUtilisateur());
            return discussionRepo.save(discussion);
        }
        throw new IllegalArgumentException("L'annonce n'existe plus");
    }

    @Override
    public Discussion modifer(Long aLong, Discussion discussion) {
        return null;
    }

    @Override
    public Optional<Discussion> trouver(Long id) {
        return discussionRepo.findById(id);
    }

    @Override
    public List<Discussion> recuperer() {
        return discussionRepo.findAll();
    }

    public List<Discussion> recupererCurrentUser() {
        Utilisateur currentUser = userService.getCurrentUser();
        return discussionRepo.findByAcheteurOrVendeur(currentUser, currentUser);
    }

    @Override
    public void supprimer(Long aLong) {

    }
}
