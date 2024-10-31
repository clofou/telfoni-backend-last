package org.bamappli.telfonibackendspring.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bamappli.telfonibackendspring.Enum.TransactionType;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HistoriqueWalletRecharge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double montant;

    @ManyToOne
    private Wallet wallet;

    @ManyToOne
    private Client client;

    private Date date;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
}
