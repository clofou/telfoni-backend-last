package org.bamappli.telfonibackendspring.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.Entity.Photos;
import org.bamappli.telfonibackendspring.Repository.PhotosRepo;
import org.bamappli.telfonibackendspring.Utils.FileOperation;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PhotosService implements CrudService<Long, Photos> {

    private final PhotosRepo photosRepo;

    @Override
    public Photos creer(Photos photos) {
        return null;
    }
    @Override
    public Photos modifer(Long aLong, Photos photos) {
        return null;
    }
    @Override
    public Optional<Photos> trouver(Long aLong) {
        return Optional.empty();
    }
    @Override
    public List<Photos> recuperer() {
        return photosRepo.findAll();
    }
    @Override
    public void supprimer(Long id) {}



    public Resource recupererPhotos(Long id) throws MalformedURLException {
        Optional<Photos> photos = photosRepo.findById(id);
        if (photos.isPresent()){
            return FileOperation.downloadFile(photos.get().getNom());
        }else{
            throw new IllegalArgumentException("La Photos n'existe pas");
        }
    }

    public Photos creerPhotos(MultipartFile file) throws IOException {
        if (file != null){
            Photos photos = new Photos();
            photos.setNom(FileOperation.uploadFile(file, "uploads/images/telephones/"));
            return photosRepo.save(photos);
        }else{
            throw new IllegalArgumentException("Aucun fichier Detecte");
        }
    }

    public void supprimerPhotos(Long id) throws IOException {
        // Récupérer l'entité Photos par l'id
        Optional<Photos> optionalPhoto = photosRepo.findById(id);
        if (optionalPhoto.isPresent()){
            FileOperation.deleteFile(optionalPhoto.get().getNom());
        }
    }


}
