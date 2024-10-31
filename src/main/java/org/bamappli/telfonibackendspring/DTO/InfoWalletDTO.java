package org.bamappli.telfonibackendspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoWalletDTO {
    private Long id;
    private double solde;
    private double soldeBloque;
    private List<WalletHistoriqueDTO> historique;
}
