package org.bamappli.telfonibackendspring.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.Entity.Promotion;
import org.bamappli.telfonibackendspring.Repository.PromotionRepo;
import org.bamappli.telfonibackendspring.Utils.FileOperation;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class BoutiqueServiceC {
    private PromotionRepo promotionRepo;

    Promotion fairePromotion(MultipartFile file, Promotion promotion) throws IOException {
        String imageUrl = FileOperation.uploadFile(file, "promotion");
        promotion.setImageUrl(imageUrl);
        return promotionRepo.save(promotion);
    }
}
