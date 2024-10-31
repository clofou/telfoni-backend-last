package org.bamappli.telfonibackendspring.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.Entity.*;
import org.bamappli.telfonibackendspring.Repository.ReparateurRepo;
import org.bamappli.telfonibackendspring.Repository.RoleRepo;
import org.bamappli.telfonibackendspring.Repository.UtilisateurRepo;
import org.bamappli.telfonibackendspring.Repository.WalletRepo;
import org.bamappli.telfonibackendspring.Utils.UserService;
import org.bamappli.telfonibackendspring.Utils.UtilService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReparateurService implements CrudService<Long, Reparateur>{

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UtilisateurRepo utilisateurRepo;
    private final ReparateurRepo reparateurRepo;
    private final RoleRepo roleRepo;
    private final WalletRepo walletRepo;

    @Override
    public Reparateur creer(Reparateur reparateur) {
        Utilisateur admin = userService.getCurrentUser();

        // Verifier si le mail et le mot de passe sont valides
        UtilService.checkEmailAndPassword(reparateur.getEmail(), reparateur.getMotDePasse());

        Utilisateur utilisateur = utilisateurRepo.findByEmail(reparateur.getEmail());
        if (utilisateur != null){
            throw new RuntimeException("Le Reparateur Existe deja");
        }

        // Attribution d'un role
        Role role = roleRepo.findByNom("REPARATEUR");
        if (role == null) throw new RuntimeException("Le role n'existe pas");
        reparateur.setRole(role);


        if(Objects.equals(admin.getRole().getNom(), "ADMIN")) reparateur.setAdmin((Admin) admin);
        reparateur.setMotDePasse(passwordEncoder.encode(reparateur.getMotDePasse()));

        // Creer Un Compte par defaut
        Wallet compte = walletRepo.save(new Wallet());
        reparateur.setCompte(compte);

        return reparateurRepo.save(reparateur);
    }

    @Override
    public Reparateur modifer(Long id, Reparateur reparateur) {
        Utilisateur user = userService.getCurrentUser();
        if (UtilService.isValidPassword(reparateur.getMotDePasse())){
            throw new IllegalArgumentException("Le Mot de passe doit comporter plus de 6 caracteres");
        }
        Optional<Reparateur> reparateur1 = reparateurRepo.findById(id);

        if (reparateur1.isPresent() && (Objects.equals(user.getRole().getNom(), "ADMIN") || Objects.equals(user.getId(), reparateur1.get().getId()))){
            Reparateur reparateurExist = reparateur1.get();
            if (reparateur.getNom() != null) reparateurExist.setNom(reparateur.getNom());
            if (reparateur.getNumeroDeTelephone() != null) reparateurExist.setNumeroDeTelephone(reparateur.getNumeroDeTelephone());
            return reparateurRepo.save(reparateurExist);
        }else{
            throw new IllegalArgumentException("La boutique n'existe pas ou l'utilisateur connecte n'as pas le droit d'effectuer cette action");
        }
    }

    @Override
    public Optional<Reparateur> trouver(Long id) {
        return reparateurRepo.findById(id);
    }

    @Override
    public List<Reparateur> recuperer() {
        return reparateurRepo.findAll();
    }

    @Override
    public void supprimer(Long id) {
        Optional<Reparateur> reparateur = reparateurRepo.findById(id);
        reparateur.ifPresent(reparateurRepo::delete);
    }
}
