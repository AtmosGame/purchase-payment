package id.ac.ui.cs.advprog.purchasepayment.exceptions.advice;

import id.ac.ui.cs.advprog.purchasepayment.exceptions.AppNotInCartException;
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
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;
    private String username;
    private String appId;

    @BeforeEach
    public void setUp() {
        username = "test_username";
        appId = "test_appId";
    }

    void TestNotAvailableException(RuntimeException exception, String expectedErrorMessage) {
        // act
        ResponseEntity<Object> response = globalExceptionHandler.notAvailable(exception);
        ErrorTemplate actualError = (ErrorTemplate) response.getBody();

        // assert
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        ZonedDateTime expectedTimestamp = ZonedDateTime.now(ZoneId.of("Z"));
        ErrorTemplate expectedError = new ErrorTemplate(expectedErrorMessage, expectedHttpStatus, expectedTimestamp);

        // assert
        Assertions.assertThat(expectedError.getHttpStatus()).isEqualTo(actualError.getHttpStatus());
        Assertions.assertThat(expectedError.getMessage()).isEqualTo(actualError.getMessage());
        Assertions.assertThat(expectedError.getTimestamp().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(actualError.getTimestamp().truncatedTo(ChronoUnit.SECONDS));
    }

    @Test
    void cartNotAvailableShouldReturnErrorResponse() {
        // arrange
        RuntimeException exception = new CartDoesNotExistException(username);
        String expectedErrorMessage = "Cart with username " + username + " does not exist";

        // test
        TestNotAvailableException(exception, expectedErrorMessage);
    }

    @Test
    void appNotAvailableShouldReturnErrorResponse() {
        // arrange
        RuntimeException exception = new AppNotInCartException(appId);
        String expectedErrorMessage = String.format("App with id:%s does not exist in cart", appId);

        // test
        TestNotAvailableException(exception, expectedErrorMessage);
    }
}