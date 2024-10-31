package org.bamappli.telfonibackendspring.Controller.advice;

import jakarta.persistence.EntityNotFoundException;
import org.bamappli.telfonibackendspring.DTO.ErrorEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApplicationControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    public @ResponseBody ErrorEntity handleException(IllegalArgumentException exception){
        return new ErrorEntity(404, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({RuntimeException.class})
    public @ResponseBody ErrorEntity handleException1(RuntimeException exception){
        return new ErrorEntity(500, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler({EntityNotFoundException.class})
    public @ResponseBody ErrorEntity handleException3(EntityNotFoundException exception){
        return new ErrorEntity(404, exception.getMessage());
    }

}
