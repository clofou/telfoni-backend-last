package org.bamappli.telfonibackendspring.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reparateur extends Utilisateur {

    @ManyToOne
    private Admin admin;

    private String docOff1;
    private String docOff2;
    private String docOff3;
}
