package org.bamappli.telfonibackendspring.Services;


import java.util.List;
import java.util.Optional;

interface CrudService<ID, ENTITY> {

    ENTITY creer(ENTITY entity);

    ENTITY modifer(ID id, ENTITY entity);

    Optional<ENTITY> trouver(ID id);

    List<ENTITY> recuperer();

    void supprimer(ID id);
}
