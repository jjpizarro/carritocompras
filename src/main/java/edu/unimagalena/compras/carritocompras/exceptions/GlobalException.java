package edu.unimagalena.compras.carritocompras.exceptions;

import java.time.LocalDateTime;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorResponse> resourceNotFound(ResourceNotFound ex, WebRequest request){
        ErrorResponse message = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            LocalDateTime.now(),
            ex.getMessage(),
            request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> globalExceptionHandle(Exception ex, WebRequest request){
        ErrorResponse message = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            LocalDateTime.now(),
            ex.getMessage(),
            request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> constraintViolationExceptionHandle(ConstraintViolationException ex, WebRequest request){
        ErrorResponse message = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now(),
            ex.getMessage(),
            request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

}
