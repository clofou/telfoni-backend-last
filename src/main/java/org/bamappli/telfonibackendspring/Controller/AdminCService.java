package org.bamappli.telfonibackendspring.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.DTO.WalletDTO;
import org.bamappli.telfonibackendspring.Entity.Client;
import org.bamappli.telfonibackendspring.Entity.HistoriqueWalletRecharge;
import org.bamappli.telfonibackendspring.Enum.TransactionType;
import org.bamappli.telfonibackendspring.Repository.ClientRepo;
import org.bamappli.telfonibackendspring.Repository.HistoriqueWalletRechargeRepo;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class AdminCService {
    private ClientRepo clientRepo;
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
}
