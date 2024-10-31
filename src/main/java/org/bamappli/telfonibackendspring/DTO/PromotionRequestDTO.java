package org.bamappli.telfonibackendspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bamappli.telfonibackendspring.Entity.Promotion;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class PromotionRequestDTO {
    private MultipartFile file;
    private Promotion promotion;
}
