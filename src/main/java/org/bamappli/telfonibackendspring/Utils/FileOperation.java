package org.bamappli.telfonibackendspring.Utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileOperation {

    // Méthode pour supprimer physiquement un fichier
    public static void deleteFile(String lienMedia) throws IOException {
        Path filePath = Paths.get(lienMedia).normalize();
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        } else {
            throw new RuntimeException("Le fichier n'existe pas : " + lienMedia);
        }
    }

    // Méthode pour uploader une photo
    public static String uploadFile(MultipartFile file, String uploadDir) throws IOException {
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String newFileName = generateUniqueFileName(originalFilename);
        Path filePath = Paths.get(uploadDir, newFileName);

        // Vérifie si le fichier existe déjà, si oui, renomme-le
        while (Files.exists(filePath)) {
            newFileName = generateUniqueFileName(originalFilename); // Renomme le fichier
            filePath = Paths.get(uploadDir, newFileName);
        }

        Files.copy(file.getInputStream(), filePath); // Sauvegarde le fichier
        return newFileName;
    }

    // Méthode pour télécharger une image à partir de l'entité Photos via lienMedias
    public static Resource downloadFile(String lienMedia) throws MalformedURLException {

        // Vérifier que le lienMedia n'est pas vide et localiser le fichier
        Path filePath = Paths.get(lienMedia).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("Le fichier n'a pas pu être trouvé ou lu : " + lienMedia);
        }
    }



    private static String generateUniqueFileName(String originalFilename) {
        String uniqueID = UUID.randomUUID().toString();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        return uniqueID + fileExtension; // Renomme le fichier avec un UUID
    }
}
