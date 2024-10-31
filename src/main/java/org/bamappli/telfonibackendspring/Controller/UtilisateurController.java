package org.bamappli.telfonibackendspring.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.DTO.*;
import org.bamappli.telfonibackendspring.Entity.Client;
import org.bamappli.telfonibackendspring.Entity.Utilisateur;
import org.bamappli.telfonibackendspring.Mapper.UserDTOMapper;
import org.bamappli.telfonibackendspring.Repository.ClientRepo;
import org.bamappli.telfonibackendspring.Repository.UtilisateurRepo;
import org.bamappli.telfonibackendspring.Services.ClientService;
import org.bamappli.telfonibackendspring.Services.EmailService;
import org.bamappli.telfonibackendspring.Utils.UserService;
import org.bamappli.telfonibackendspring.Utils.UtilService;
import org.bamappli.telfonibackendspring.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class UtilisateurController {

    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private ClientService clientService;
    private final ClientRepo clientRepo;;
    private UserService userService;
    private UserDTOMapper userDTOMapper;
    private UtilisateurRepo utilisateurRepo;
    private EmailService emailService;

    @PostMapping(path = "connexion")
    public Map<String, String> seConnecter(@RequestBody AuthentificationDTO auth) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(auth.getEmail(), auth.getMotDePasse())
        );
        if (authentication.isAuthenticated()){
            return jwtService.generate(auth.getEmail());
        }
        return null;
    }

    @PostMapping(path = "inscription")
    public Client inscription(@RequestBody InscriptionDTO dto){
        // Génération du code de vérification
        String verificationCode = UtilService.generateVerificationCode();

        Client client = new Client();
        client.setNom(dto.getNom());
        client.setFcmToken(dto.getFcmToken());
        client.setVerificationCode(verificationCode);
        client.setActive(false); // Le compte est inactif jusqu'à validation
        client.setEmail(dto.getEmail());
        client.setMotDePasse(dto.getMotDePasse());
        // Envoi de l'email avec le code de vérification
        emailService.sendVerificationEmail(dto.getEmail(), verificationCode);
        Client clientExist = clientRepo.findByEmail(dto.getEmail());
        if (clientExist != null && !clientExist.isActive()){
            clientExist.setVerificationCode(verificationCode);
            return clientRepo.save(clientExist);
        }else{
            return clientService.creer(client);
        }

    }

    @GetMapping(path = "user/current")
    public UtilisateurDTO getCurrentUserInfo(){
        return userDTOMapper.apply(userService.getCurrentUser());
    }

    @PostMapping("/update-token")
    public ResponseEntity<String> updateFcmToken(@RequestBody UpdateFcmTokenDTO updateFcmTokenDTO) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepo.findById(updateFcmTokenDTO.getUserId());
        if (utilisateurOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();
            utilisateur.setFcmToken(updateFcmTokenDTO.getToken());
            utilisateurRepo.save(utilisateur);
            return ResponseEntity.ok("FCM token updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping(path = "verify")
    public ResponseEntity<String> verifyAccount(@RequestBody VerifyCodeDTO verifyCodeDTO) {
        Utilisateur utilisateurOpt = utilisateurRepo.findByEmail(verifyCodeDTO.getEmail());

        if (utilisateurOpt != null) {

            // Vérification du code
            if (utilisateurOpt.getVerificationCode().equals(verifyCodeDTO.getCode())) {
                utilisateurOpt.setActive(true); // Activer le compte
                utilisateurOpt.setVerificationCode(null); // Supprimer le code de vérification après succès
                utilisateurRepo.save(utilisateurOpt);
                return ResponseEntity.ok("Votre compte a été activé avec succès.");
            } else {
                throw new IllegalArgumentException("Code incorrect");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé.");
        }
    }

}
