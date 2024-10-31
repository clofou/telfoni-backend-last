package org.bamappli.telfonibackendspring.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.Entity.Utilisateur;
import org.bamappli.telfonibackendspring.Entity.Wallet;
import org.bamappli.telfonibackendspring.Repository.WalletRepo;
import org.bamappli.telfonibackendspring.Utils.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WalletService implements CrudService<Long, Wallet>{

    private final UserService userService;
    private final WalletRepo walletRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Wallet creer(Wallet wallet) {
        wallet.setCodeSecret(passwordEncoder.encode(wallet.getCodeSecret()));
        return walletRepo.save(wallet);
    }

    @Override
    public Wallet modifer(Long id, Wallet wallet) {
        Utilisateur user = userService.getCurrentUser();
        Optional<Wallet> wallet1 = walletRepo.findById(id);

        if (wallet1.isPresent() && Objects.equals(user.getRole().getNom(), "ADMIN")){
            Wallet walletExist = wallet1.get();
            if (wallet.getSolde() != null) walletExist.setSolde(wallet.getSolde());
            if (wallet.getCodeSecret() != null) walletExist.setCodeSecret(wallet.getCodeSecret());
            if (wallet.getDevise() != null) walletExist.setDevise(wallet.getDevise());
            return walletRepo.save(walletExist);
        }else{
            throw new IllegalArgumentException("Le wallet n'existe pas ou l'utilisateur connecte n'as pas le droit d'effectuer cette action");
        }
    }

    @Override
    public Optional<Wallet> trouver(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Wallet> recuperer() {
        return List.of();
    }

    @Override
    public void supprimer(Long aLong) {

    }
}
