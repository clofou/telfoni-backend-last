package org.bamappli.telfonibackendspring;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.Entity.Admin;
import org.bamappli.telfonibackendspring.Entity.Role;
import org.bamappli.telfonibackendspring.Entity.Wallet;
import org.bamappli.telfonibackendspring.Repository.AdminRepo;
import org.bamappli.telfonibackendspring.Repository.RoleRepo;
import org.bamappli.telfonibackendspring.Repository.WalletRepo;
import org.bamappli.telfonibackendspring.Services.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private RoleService roleService;
    private AdminRepo adminRepo;
    private PasswordEncoder passwordEncoder;
    private RoleRepo roleRepo;
    private WalletRepo walletRepo;

    @Override
    public void run(String... args){
        Role role1 = new Role(null, "ADMIN");
        Role role2 = new Role(null, "CLIENT");
        Role role3 = new Role(null, "BOUTIQUE");
        Role role4 = new Role(null, "REPARATEUR");
        Role role5 = new Role(null, "CONTROLLER");

        roleService.creerRole(role1);
        roleService.creerRole(role2);
        roleService.creerRole(role3);
        roleService.creerRole(role4);
        roleService.creerRole(role5);

        Admin admin = new Admin();
        admin.setEmail("fakoro88@gmail.com");
        admin.setActive(true);
        admin.setMotDePasse(passwordEncoder.encode("090909"));
        admin.setNom("Fakoro");

        Role role = roleRepo.findByNom("ADMIN");
        if (role == null){
            throw new RuntimeException("Le role n'existe pas");
        }
        admin.setRole(role);



        Admin admin1 = adminRepo.findByEmail(admin.getEmail());
        if (admin1 == null){
            Wallet compte = walletRepo.save(new Wallet());

            admin.setCompte(compte);
            adminRepo.save(admin);
        }

    }
}