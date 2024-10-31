package org.bamappli.telfonibackendspring.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bamappli.telfonibackendspring.Enum.TelephoneType;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Telephone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TelephoneType type;
    private String titre;
    private Integer memoire;
    @Column(length = 2048)
    private String description;
    private Double prix;
    private Date date = new Date();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Tags> tagsList;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Photos> photosList;

    @ManyToOne
    private Brand brand;
    @ManyToOne
    private Modele modele;

    @ManyToOne
    private Utilisateur utilisateur;
}
