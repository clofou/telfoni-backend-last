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
public class Controller extends Utilisateur{
    private String docOff1;
    private String docOff2;
    private String docOff3;

    @ManyToOne
    private Admin admin;
}
