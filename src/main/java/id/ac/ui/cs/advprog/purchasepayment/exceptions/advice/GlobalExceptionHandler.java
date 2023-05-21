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
            AppNotInCartException.class,
            RequestDataInvalidException.class,
            AppAlreadyInPurchasedAppException.class,
            AppAlreadyInCartException.class,
            AppAlreadyInCheckoutException.class,
            CheckoutIsExpiredException.class,
            AppIsNotAvailableException.class,
            UsernameIsNotAvailableException.class,
            CheckoutIsExpiredException.class
    })
    public ResponseEntity<Object> notAvailable(Exception exception) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        var baseException = new ErrorTemplate(
                exception.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(baseException, badRequest);
    }

    @ExceptionHandler(value = {SecretTokenInvalidException.class})
    public ResponseEntity<Object> secretTokenInvalid(Exception exception) {
        var baseException = new CustomErrorTemplate(
                exception.getMessage(),
                "INVALID_TOKEN",
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return ResponseEntity.status(498).body(baseException);
    }
}
