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
public class Boutique extends Utilisateur {
    private boolean accountLocked;
    private String description;
    @ManyToOne
    private Admin admin;
}
