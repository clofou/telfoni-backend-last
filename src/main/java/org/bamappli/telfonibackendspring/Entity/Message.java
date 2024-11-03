package org.bamappli.telfonibackendspring.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bamappli.telfonibackendspring.DTO.PaymentCard;
import org.bamappli.telfonibackendspring.Enum.MessageType;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private Date timestamp = new Date();
    @Enumerated(EnumType.STRING)
    private MessageType messageType;
    private String fileUrl;

    @ManyToOne
    private Utilisateur utilisateur;

    @ManyToOne
    private Discussion discussion;

    // Spécifique à la carte de paiement
    @Embedded
    private PaymentCard paymentCard;
}
