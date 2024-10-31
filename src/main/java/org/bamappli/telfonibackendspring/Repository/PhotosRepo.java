package org.bamappli.telfonibackendspring.Repository;


import org.bamappli.telfonibackendspring.Entity.Photos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotosRepo extends JpaRepository<Photos, Long> {

}
