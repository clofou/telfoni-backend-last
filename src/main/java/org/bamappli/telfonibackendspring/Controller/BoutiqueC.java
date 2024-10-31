package org.bamappli.telfonibackendspring.Controller;

import lombok.AllArgsConstructor;

import org.bamappli.telfonibackendspring.DTO.PromotionRequestDTO;
import org.bamappli.telfonibackendspring.Entity.Promotion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "boutique")
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class BoutiqueC {
    private BoutiqueServiceC boutiqueServiceC;

    @PostMapping
    public ResponseEntity<Promotion> faireUnePromotion(@RequestBody PromotionRequestDTO promotion) throws IOException {
        return ResponseEntity.ok(boutiqueServiceC.fairePromotion(promotion.getFile(), promotion.getPromotion()));
    }
}
