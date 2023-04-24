package id.ac.ui.cs.advprog.purchasepayment.exceptions.advice;

import id.ac.ui.cs.advprog.purchasepayment.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {
            CartDoesNotExistException.class,
            RequestDataIsNotValidException.class,
            AppAlreadyInListException.class,
            AppAlreadyInCartException.class,
            AppAlreadyInCheckoutException.class,
    })
    public ResponseEntity<Object> cartNotAvailable(Exception exception) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        var baseException = new ErrorTemplate(
                exception.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(baseException, badRequest);
    }
}
