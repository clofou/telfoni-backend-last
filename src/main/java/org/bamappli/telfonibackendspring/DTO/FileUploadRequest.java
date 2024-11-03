package org.bamappli.telfonibackendspring.DTO;

import lombok.*;
import org.bamappli.telfonibackendspring.Enum.MessageType;

import java.util.List;

@Data
@AllArgsConstructor
@Getter
@Setter
public class FileUploadRequest {
    private String message;
    private MessageType messageType;
    private List<FileData> files;
    private PaymentCard paymentCard;

    // Getters et setters

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FileData {
        private String filename;
        private String content; // Encodé en base64

        // Getters et setters
    }
}