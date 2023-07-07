package com.vbuttini.demo.shoe.controller.exceptions;

import com.vbuttini.demo.shoe.service.exceptions.ShoeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

/**
 * This class is a Custom Exception Handler
 *
 * @author Vinícius Buttini
 */
@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ShoeNotFoundException.class)
    public ResponseEntity<StandardError> shoeNotFoundException(ShoeNotFoundException e, HttpServletRequest request){
        String error = "Database Error";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(),status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

}