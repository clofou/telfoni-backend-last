package org.bamappli.telfonibackendspring.Mapper;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.DTO.ControllerModel;
import org.bamappli.telfonibackendspring.Entity.Controller;
import org.bamappli.telfonibackendspring.Repository.CommandeRepo;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@AllArgsConstructor
public class ControllerDTOMapper implements Function<Controller, ControllerModel> {
    private CommandeRepo commandeRepo;

    @Override
    public ControllerModel apply(Controller controller) {

        Integer nbreDeControl = commandeRepo.controllTotal(controller.getId());

        if (nbreDeControl == null){
            nbreDeControl = 0;
        }

        return new ControllerModel(controller.getId(), controller.getNom(), controller.getRating(), controller.getAdresse(),
                controller.getNumeroDeTelephone(), controller.getEmail(), controller.getGrade(), nbreDeControl);
    }
}
