package org.bamappli.telfonibackendspring.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bamappli.telfonibackendspring.Enum.Devise;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double solde = (double) 0;
    private Double montantBloque = (double) 0;
    @Enumerated(EnumType.STRING)
    private Devise devise = Devise.CFA;
    private String codeSecret;
}
