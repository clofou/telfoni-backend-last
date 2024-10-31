package org.bamappli.telfonibackendspring.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bamappli.telfonibackendspring.Enum.Grade;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String email;
    private String motDePasse;
    private String numeroDeTelephone;
    private String adresse;
    private String photoUrl;
    private Date dateCreation = new Date();
    private String fcmToken;
    private int rating = 0;
    private int totalRating = 0;
    private int points = 0;
    private String verificationCode; // Code de vérification
    private boolean isActive = false;

    @Enumerated(EnumType.STRING)
    private Grade grade = Grade.BASIC;

    @ManyToOne
    private Role role;

    @OneToOne
    private Wallet compte;


}
