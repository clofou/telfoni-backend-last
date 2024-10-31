package org.bamappli.telfonibackendspring.Repository;

import org.bamappli.telfonibackendspring.Entity.Telephone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelephoneRepo extends JpaRepository<Telephone, Long> {

}