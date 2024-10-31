package org.bamappli.telfonibackendspring.DTO;

import lombok.Data;
import org.bamappli.telfonibackendspring.Entity.Transaction;

@Data
public class PayerTransactionDTO {
    private String codeSecret;
    private Transaction transaction;
}
