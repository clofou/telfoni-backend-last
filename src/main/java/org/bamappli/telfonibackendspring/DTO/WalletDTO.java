package org.bamappli.telfonibackendspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WalletDTO {
    private String email;
    private int montant;
    private String receiverEmail;
}
