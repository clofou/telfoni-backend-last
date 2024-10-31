package org.bamappli.telfonibackendspring.Services;


import org.bamappli.telfonibackendspring.Entity.Admin;
import org.bamappli.telfonibackendspring.Entity.Role;
import org.bamappli.telfonibackendspring.Entity.Utilisateur;
import org.bamappli.telfonibackendspring.Entity.Wallet;
import org.bamappli.telfonibackendspring.Repository.AdminRepo;
import org.bamappli.telfonibackendspring.Repository.RoleRepo;
import org.bamappli.telfonibackendspring.Repository.UtilisateurRepo;
import org.bamappli.telfonibackendspring.Utils.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AdminService{

    private AdminRepo adminRepo;
    private RoleRepo roleRepo;
    private UtilisateurRepo utilisateurRepo;
    private PasswordEncoder passwordEncoder;
    private WalletService walletService;

    public Admin creer(Admin admin) {
        // Verifier si le mail et le mot de passe sont valides
        UtilService.checkEmailAndPassword(admin.getEmail(), admin.getMotDePasse());


        Utilisateur utilisateur = utilisateurRepo.findByEmail(admin.getEmail());
        if (utilisateur != null){
            throw new RuntimeException("L'utilisateur existe deja");
        }

        // Attribution d'un role
        Role role = roleRepo.findByNom("ADMIN");
        if (role == null){
            throw new RuntimeException("Le role n'existe pas");
        }
        admin.setRole(role);

        admin.setMotDePasse(passwordEncoder.encode(admin.getMotDePasse()));

        // Creer Un Compte par defaut
        Wallet compte = new Wallet();
        compte.setCodeSecret("000000");
        admin.setCompte(walletService.creer(compte));

        return adminRepo.save(admin);
    }
    public Admin modifer(Long id, Admin admin){
        Optional<Admin> admin1 = adminRepo.findById(id);
        if (admin1.isPresent()){
            Admin theAdmin = admin1.get();
            if (admin.getNom() != null) theAdmin.setNom(admin.getNom());
            if (admin.getNumeroDeTelephone() != null) theAdmin.setNumeroDeTelephone(admin.getNumeroDeTelephone());
            return adminRepo.save(theAdmin);
        }
        return null;
    }

    public Optional<Admin> trouver(Long id){
        return adminRepo.findById(id);
    }


    public List<Admin> recuperer(){
        return adminRepo.findAll();
    }


    public void supprimer(Long id){
        Optional<Admin> admin1 = adminRepo.findById(id);
        admin1.ifPresent(adminRepo::delete);
    }
}
