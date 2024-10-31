package org.bamappli.telfonibackendspring.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.Entity.Transaction;
import org.bamappli.telfonibackendspring.Entity.Utilisateur;
import org.bamappli.telfonibackendspring.Enum.AnnonceStatut;
import org.bamappli.telfonibackendspring.Enum.TransactionStatut;
import org.bamappli.telfonibackendspring.Repository.TransactionRepo;
import org.bamappli.telfonibackendspring.Repository.UtilisateurRepo;
import org.bamappli.telfonibackendspring.Utils.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TransactionService implements CrudService<Long, Transaction>{

    private final UserService userService;
    private final TransactionRepo transactionRepo;
    private final UtilisateurRepo utilisateurRepo;
    private final PasswordEncoder passwordEncoder;
    private final CommandeService commandeService;

    @Override
    public Transaction creer(Transaction transaction) {
        Optional<Utilisateur> client = utilisateurRepo.findById(transaction.getAcheteur().getId());
        if (client.isPresent()){
            return transactionRepo.save(transaction);
        }
        throw new EntityNotFoundException("Utilisateur non trouve");
    }

    @Override
    public Transaction modifer(Long id, Transaction transaction) {
        Optional<Transaction> transaction1 = transactionRepo.findById(id);

        if (transaction1.isPresent()){
            Transaction transactionExist = transaction1.get();
            if (transaction.getStatut() != null) transactionExist.setStatut(transaction.getStatut());
            return transactionRepo.save(transactionExist);
        }else{
            throw new IllegalArgumentException("La Transaction n'existe pas ou l'utilisateur connecte n'as pas le droit d'effectuer cette action");
        }
    }

    @Override
    public Optional<Transaction> trouver(Long id) {
        return transactionRepo.findById(id);
    }

    @Override
    public List<Transaction> recuperer() {
        return transactionRepo.findAll();
    }

    @Override
    public void supprimer(Long id) {
        transactionRepo.deleteById(id);
    }

    public Transaction payer(Long id, String codeSecret) {
        Utilisateur utilisateur = userService.getCurrentUser();
        Optional<Transaction> transaction1 = transactionRepo.findById(id);
        System.out.println(utilisateur.getCompte().getCodeSecret());
        System.out.println(passwordEncoder.encode(codeSecret));
        if (Objects.equals(passwordEncoder.encode(codeSecret), utilisateur.getCompte().getCodeSecret())){
            if (transaction1.isPresent()){
                Transaction transactionExist = transaction1.get();
                if (transactionExist.getMontant() <= utilisateur.getCompte().getSolde()){
                    transactionExist.setDateDeTransaction(new Date());
                    transactionExist.setStatut(TransactionStatut.PAYER);
                    transactionExist.getPhone().setStatut(AnnonceStatut.VENDU);
                    return transactionRepo.save(transactionExist);
                }else{
                    throw new IllegalArgumentException("Solde Insuffisant");
                }

            }else{
                throw new IllegalArgumentException("La Transaction n'existe pas ou l'utilisateur connecte n'as pas le droit d'effectuer cette action");
            }
        }else{
            throw new IllegalArgumentException("Votre code est errone");
        }


    }
}
