package org.bamappli.telfonibackendspring.Mapper;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.DTO.AnnonceDTO;
import org.bamappli.telfonibackendspring.Entity.Annonce;
import org.bamappli.telfonibackendspring.Entity.Client;
import org.bamappli.telfonibackendspring.Entity.Photos;
import org.bamappli.telfonibackendspring.Entity.Tags;
import org.bamappli.telfonibackendspring.Repository.FavoriteRepo;
import org.bamappli.telfonibackendspring.Repository.StockRepo;
import org.bamappli.telfonibackendspring.Utils.UserService;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AnnonceDTOMapperC implements Function<Annonce, AnnonceDTO> {
    private final UserService userService;
    private final FavoriteRepo favoriteRepo;
    private final StockRepo stockRepo;

    @Override
    public AnnonceDTO apply(Annonce annonce) {
        boolean isLisked = false;
        Integer totalLikes = favoriteRepo.Lannonceapparaitdanscombiendepanier(annonce.getId());
        String typeVendeur = "";
        if (annonce.getUtilisateur() instanceof Client){
            typeVendeur = "Client";
        }else{
            typeVendeur = "Boutique";
        }
        int quantite = 1;
        Integer nombre = stockRepo.inStock(annonce.getPhone().getId());
        if (nombre != null){
            quantite = nombre;
        }
        Long brand = 0L;
        if (annonce.getPhone().getBrand() != null){
            brand = annonce.getPhone().getBrand().getId();
        }


        return new AnnonceDTO(
                annonce.getId(),
                annonce.getPhone().getTitre(),
                annonce.getPhone().getPhotosList().stream()
                .map(Photos::getNom) // Récupère les noms des photos
                .collect(Collectors.toList()),
                annonce.getPhone().getPrix().toString(),
                isLisked,
                annonce.getPhone().getDescription(),
                annonce.getPhone().getType(),
                annonce.getUtilisateur().getNom(),
                typeVendeur,
                annonce.getUtilisateur().getGrade().toString(),
                quantite,
                totalLikes,
                annonce.getPhone().getDate(),
                annonce.getPhone().getTagsList().stream()
                        .map(Tags::getNom) // Récupère les noms des photos
                        .collect(Collectors.toList()),
                brand,
                annonce.getStatut()

        );
    }
}
