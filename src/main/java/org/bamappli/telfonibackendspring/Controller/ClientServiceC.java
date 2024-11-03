package org.bamappli.telfonibackendspring.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.DTO.FileUploadRequest;
import org.bamappli.telfonibackendspring.DTO.PaymentCard;
import org.bamappli.telfonibackendspring.Entity.Annonce;
import org.bamappli.telfonibackendspring.Entity.Client;
import org.bamappli.telfonibackendspring.Entity.Message;
import org.bamappli.telfonibackendspring.Enum.MessageType;
import org.bamappli.telfonibackendspring.Repository.AnnonceRepo;
import org.bamappli.telfonibackendspring.Repository.ClientRepo;
import org.bamappli.telfonibackendspring.Repository.MessageRepo;
import org.bamappli.telfonibackendspring.Utils.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@AllArgsConstructor
public class ClientServiceC {
    private final ClientRepo clientRepo;
    private final AnnonceRepo annonceRepo;
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageRepo messageRepo;
    private static final String UPLOAD_DIR = "src/main/resources/static/message/";

    void likerAnnonce(Long idAnnonce){
        Client currentUser = (Client) userService.getCurrentUser();
        Optional<Annonce> annonce = annonceRepo.findById(idAnnonce);
        if(annonce.isPresent()){
            System.out.println("Annonce liked!");
            if (!currentUser.getFavorite().getAnnonceList().contains(annonce.get())){
                currentUser.getFavorite().getAnnonceList().add(annonce.get());
            }else{
                currentUser.getFavorite().getAnnonceList().remove(annonce.get());
            }
        }

        clientRepo.save(currentUser);
    }

    public ResponseEntity<String> sendMessage(FileUploadRequest request) {
        try {
            List<String> fileUrls = new ArrayList<>();

            // Parcourir les fichiers envoyés dans la requête
            for (FileUploadRequest.FileData fileData : request.getFiles()) {
                // Décoder le contenu base64
                byte[] decodedBytes = Base64.getDecoder().decode(fileData.getContent());
                String uniqueFileName = UUID.randomUUID() + "_" + fileData.getFilename();
                Path filePath = Paths.get(UPLOAD_DIR + uniqueFileName);

                // Créer le dossier si nécessaire
                Files.createDirectories(filePath.getParent());

                // Enregistrer le fichier
                Files.write(filePath, decodedBytes);

                // Déterminer le type MIME
                String mimeType = Files.probeContentType(filePath);
                if (mimeType == null) {
                    mimeType = "unknown";
                }

                // Déduire le type de fichier à partir du type MIME
                MessageType messageType;
                if (mimeType.startsWith("image/")) {
                    messageType = MessageType.PHOTOS;
                } else if (mimeType.startsWith("video/")) {
                    messageType = MessageType.VIDEOS;
                } else if (mimeType.startsWith("audio/")) {
                    messageType = MessageType.SONG;
                } else {
                    messageType = MessageType.TEXT;
                }

                // Ajouter l'URL du fichier à la liste des résultats
                String fileUrl = "/uploads/" + uniqueFileName;
                fileUrls.add(fileUrl);

                // Créer un objet Message avec le type de fichier déterminé
                Message message = new Message();
                message.setMessage(fileData.getFilename()); // Exemple de texte associé
                message.setMessageType(messageType);
                message.setTimestamp(new Date());
                // Autres champs de `Message` à remplir

                // Sauvegarder le message (si nécessaire, avec un repository)
                messageRepo.save(message);
            }

            if (request.getMessageType() == MessageType.PAIEMENT_CARD) {
                // Logique pour traiter une carte de paiement
                Message message = getMessage(request);

                // Sauvegarder le message (si nécessaire, avec un repository)
                messageRepo.save(message);

                // Diffuser le message via WebSocket
                messagingTemplate.convertAndSend("/topic/messages", message);
            }
            // Créer un message pour la diffusion via WebSocket
            Map<String, Object> messageData = new HashMap<>();
            messageData.put("message", request.getMessage());
            messageData.put("messageType", request.getMessageType());
            messageData.put("fileUrls", fileUrls);
            messageData.put("timestamp", new Date());

            // Diffuser le message à tous les abonnés de "/topic/messages"
            messagingTemplate.convertAndSend("/topic/messages", messageData);

            return ResponseEntity.ok("Message envoyé avec succès. Fichiers : " + String.join(", ", fileUrls));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'upload des fichiers : " + e.getMessage());
        }
    }

    private static Message getMessage(FileUploadRequest request) {
        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setAmount(request.getPaymentCard().getAmount());
        paymentCard.setDescription(request.getPaymentCard().getDescription());
        paymentCard.setPaymentUrl(request.getPaymentCard().getPaymentUrl());

        // Créer un message avec les détails de la carte de paiement
        Message message = new Message();
        message.setMessage("Carte de paiement envoyée"); // Message générique
        message.setMessageType(MessageType.PAIEMENT_CARD);
        message.setPaymentCard(paymentCard);
        message.setTimestamp(new Date());
        return message;
    }
}
