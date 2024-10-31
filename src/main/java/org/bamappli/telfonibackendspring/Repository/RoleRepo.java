package org.bamappli.telfonibackendspring.Repository;


import org.bamappli.telfonibackendspring.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByNom(String nom);
}
