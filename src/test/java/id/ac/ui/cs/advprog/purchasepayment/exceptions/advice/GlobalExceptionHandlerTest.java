package id.ac.ui.cs.advprog.purchasepayment.exceptions.advice;

import id.ac.ui.cs.advprog.purchasepayment.exceptions.CartDoesNotExistException;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.ErrorTemplate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;
    private String username;

    @BeforeEach
    public void setUp() {
        username = "test_username";
    }

    @Test
    public void cartNotAvailable_shouldReturnErrorResponse() {
        // arrange
        CartDoesNotExistException exception = new CartDoesNotExistException(username);

        // act
        ResponseEntity<Object> response = globalExceptionHandler.cartNotAvailable(exception);
        ErrorTemplate actualError = (ErrorTemplate) response.getBody();

        // assert
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        String expectedErrorMessage = "Cart with username " + username + " does not exist";
        ZonedDateTime expectedTimestamp = ZonedDateTime.now(ZoneId.of("Z"));
        ErrorTemplate expectedError = new ErrorTemplate(expectedErrorMessage, expectedHttpStatus, expectedTimestamp);

        // assert
        Assertions.assertThat(expectedError.httpStatus()).isEqualTo(actualError.httpStatus());
        Assertions.assertThat(expectedError.message()).isEqualTo(actualError.message());
        Assertions.assertThat(expectedError.timestamp().truncatedTo(ChronoUnit.SECONDS))
                .isEqualTo(actualError.timestamp().truncatedTo(ChronoUnit.SECONDS));
    }
}