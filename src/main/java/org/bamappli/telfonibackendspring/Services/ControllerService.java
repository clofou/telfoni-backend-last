package org.bamappli.telfonibackendspring.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.Entity.*;
import org.bamappli.telfonibackendspring.Repository.ControllerRepo;
import org.bamappli.telfonibackendspring.Repository.RoleRepo;
import org.bamappli.telfonibackendspring.Repository.UtilisateurRepo;
import org.bamappli.telfonibackendspring.Utils.UserService;
import org.bamappli.telfonibackendspring.Utils.UtilService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ControllerService implements CrudService<Long, Controller>{

    private final UserService userService;
    private final UtilisateurRepo utilisateurRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final WalletService walletService;
    private final ControllerRepo controllerRepo;


    @Override
    public Controller creer(Controller controller) {
        Utilisateur admin = userService.getCurrentUser();

        // Verifier si le mail et le mot de passe sont valides
        UtilService.checkEmailAndPassword(controller.getEmail(), controller.getMotDePasse());

        Utilisateur utilisateur = utilisateurRepo.findByEmail(controller.getEmail());
        if (utilisateur != null){
            throw new RuntimeException("Le Controlleur Existe deja existe deja");
        }

        // Attribution d'un role
        Role role = roleRepo.findByNom("CONTROLLER");
        if (role == null) throw new RuntimeException("Le role n'existe pas");

        controller.setRole(role);

        if(Objects.equals(admin.getRole().getNom(), "ADMIN")) controller.setAdmin((Admin) admin);
        controller.setMotDePasse(passwordEncoder.encode(controller.getMotDePasse()));

        // Creer Un Compte par defaut
        Wallet wallet = new Wallet();
        wallet.setCodeSecret("000000");
        Wallet compte = walletService.creer(wallet);
        controller.setCompte(compte);

        return controllerRepo.save(controller);
    }

    @Override
    public Controller modifer(Long id, Controller controller) {
        Utilisateur user = userService.getCurrentUser();
        if (UtilService.isValidPassword(controller.getMotDePasse())){
            throw new IllegalArgumentException("Le Mot de passe doit comporter plus de 6 caracteres");
        }
        Optional<Controller> controller1 = controllerRepo.findById(id);

        if (controller1.isPresent() && (Objects.equals(user.getRole().getNom(), "ADMIN") || Objects.equals(user.getId(), controller1.get().getId()))){
            Controller controllerExist = controller1.get();
            if (controller.getNom() != null) controllerExist.setNom(controller.getNom());
            if (controller.getNumeroDeTelephone() != null) controllerExist.setNumeroDeTelephone(controller.getNumeroDeTelephone());
            return controllerRepo.save(controllerExist);
        }else{
            throw new IllegalArgumentException("Le Controller n'existe pas ou l'utilisateur connecte n'as pas le droit d'effectuer cette action");
        }
    }

    @Override
    public Optional<Controller> trouver(Long id) {
        return controllerRepo.findById(id);
    }

    @Override
    public List<Controller> recuperer() {
        return controllerRepo.findAll();
    }

    @Override
    public void supprimer(Long id) {
        Optional<Controller> controller = controllerRepo.findById(id);
        controller.ifPresent(controllerRepo::delete);
    }
}
