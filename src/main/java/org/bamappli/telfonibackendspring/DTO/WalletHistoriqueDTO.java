package org.bamappli.telfonibackendspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bamappli.telfonibackendspring.Enum.TransactionType;

import java.util.Date;

@Data
@AllArgsConstructor
public class WalletHistoriqueDTO {
    private String nom;
    private TransactionType type;
    private Date transactionDate;
    private double montant;

}
