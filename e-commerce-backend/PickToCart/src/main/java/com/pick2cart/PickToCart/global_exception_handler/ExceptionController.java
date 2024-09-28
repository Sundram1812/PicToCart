package com.pick2cart.PickToCart.global_exception_handler;

import com.pick2cart.PickToCart.response.ExceptionRespone;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionRespone> badCredentialsException(BadCredentialsException ex){
        ExceptionRespone exceptionRespone = ExceptionRespone.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .build();


        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionRespone);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionRespone> usernameNotFoundException(UsernameNotFoundException ex){
        ExceptionRespone exceptionRespone = ExceptionRespone.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionRespone);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionRespone> allExceptionHandledHere(Exception ex){
        ExceptionRespone exceptionRespone = ExceptionRespone.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionRespone);
    }
}
