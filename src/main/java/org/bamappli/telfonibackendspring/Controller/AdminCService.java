package org.bamappli.telfonibackendspring.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.DTO.WalletDTO;
import org.bamappli.telfonibackendspring.Entity.Client;
import org.bamappli.telfonibackendspring.Entity.HistoriqueWalletRecharge;
import org.bamappli.telfonibackendspring.Enum.TransactionType;
import org.bamappli.telfonibackendspring.Repository.ClientRepo;
import org.bamappli.telfonibackendspring.Repository.HistoriqueWalletRechargeRepo;
import org.bamappli.telfonibackendspring.Repository.UtilisateurRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AdminCService {
    private ClientRepo clientRepo;
    private UtilisateurRepo utilisateurRepo;
    private HistoriqueWalletRechargeRepo walletRechargeRepo;

    void rechargerCompte(WalletDTO walletDTO) {
        Client client = clientRepo.findByEmail(walletDTO.getEmail());
        if (client != null) {
            if (walletDTO.getMontant() > 0){
                client.getCompte().setSolde(client.getCompte().getSolde() + walletDTO.getMontant());

                HistoriqueWalletRecharge recharge = new HistoriqueWalletRecharge();
                recharge.setDate(new Date());
                recharge.setTransactionType(TransactionType.DEPOT);
                recharge.setWallet(client.getCompte());
                recharge.setMontant(walletDTO.getMontant());
                walletRechargeRepo.save(recharge);

                clientRepo.save(client);
            }else{
                throw new IllegalArgumentException("Montant null ou negatif");
            }
        }
    }

    void retirerArgent(WalletDTO walletDTO) {
        Client client = clientRepo.findByEmail(walletDTO.getEmail());
        if (client != null) {
            if (client.getCompte().getSolde() - walletDTO.getMontant() > 0){
                client.getCompte().setSolde(client.getCompte().getSolde() - walletDTO.getMontant());

                HistoriqueWalletRecharge recharge = new HistoriqueWalletRecharge();
                recharge.setDate(new Date());
                recharge.setTransactionType(TransactionType.RETRAIT);
                recharge.setWallet(client.getCompte());
                recharge.setMontant(walletDTO.getMontant());
                walletRechargeRepo.save(recharge);

                clientRepo.save(client);
            }else{
                throw new IllegalArgumentException("Solde Insuffisant");
            }
        }
    }

    void transfer(WalletDTO walletDTO) {
        Client client = clientRepo.findByEmail(walletDTO.getEmail());
        Client receiver = clientRepo.findByEmail(walletDTO.getEmail());

        if (client != null && receiver != null) {

            if (client.getCompte().getSolde() - walletDTO.getMontant() > 0){
                client.getCompte().setSolde(client.getCompte().getSolde() - walletDTO.getMontant());
                receiver.getCompte().setMontantBloque(receiver.getCompte().getSolde() + walletDTO.getMontant());

                HistoriqueWalletRecharge recharge = new HistoriqueWalletRecharge();
                recharge.setDate(new Date());
                recharge.setTransactionType(TransactionType.TRANSFERT);
                recharge.setWallet(client.getCompte());
                recharge.setMontant(walletDTO.getMontant());
                walletRechargeRepo.save(recharge);
            }
           }
    }

    public Map<String, Object> getNewUsersStats() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        Long newUsersToday = utilisateurRepo.countNewUsersToday(startOfDay, endOfDay);
        System.out.println("0000000000"+newUsersToday);
        Long newUsersYesterday = utilisateurRepo.countNewUsersYesterday();

        // Calcul du pourcentage d'augmentation
        double percentageIncrease = 0;
        if (newUsersYesterday > 0) {
            percentageIncrease = ((double)(newUsersToday - newUsersYesterday) / newUsersYesterday) * 100;
        } else if (newUsersToday > 0) {
            // Si il n'y avait pas de nouveaux utilisateurs hier, l'augmentation est de 100%
            percentageIncrease = 100;
        }

        // Préparer la réponse
        Map<String, Object> stats = new HashMap<>();
        stats.put("newUsersToday", newUsersToday);
        stats.put("percentageIncrease", percentageIncrease);

        return stats;
    }

}


