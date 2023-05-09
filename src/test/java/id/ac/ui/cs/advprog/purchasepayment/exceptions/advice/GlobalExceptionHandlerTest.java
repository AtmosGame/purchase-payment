package id.ac.ui.cs.advprog.purchasepayment.exceptions.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.purchasepayment.controller.PurchaseAndPaymentController;
import id.ac.ui.cs.advprog.purchasepayment.dto.*;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.AppNotInCartException;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.CartDoesNotExistException;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.ErrorTemplate;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.SecretTokenInvalidException;
import id.ac.ui.cs.advprog.purchasepayment.web.logic.PurchaseAndPaymentLogic;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PurchaseAndPaymentController.class)
class GlobalExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PurchaseAndPaymentLogic<UpdateCartRequest, Void> updateCartLogic;

    @MockBean
    private PurchaseAndPaymentLogic<Void, GetCartResponse> getCartLogic;

    @MockBean
    private PurchaseAndPaymentLogic<UpdatePaymentRequest, Void> updatePaymentLogic;

    @MockBean
    private PurchaseAndPaymentLogic<AddSecretTokenRequest, Void> addSecretTokenLogic;

    @MockBean
    private PurchaseAndPaymentLogic<CheckoutCartRequest, Void> checkoutCartLogic;

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

    @Test
    void testSecretTokenInvalid() throws Exception {
        UpdatePaymentRequest updatePaymentRequest = UpdatePaymentRequest.builder()
                .id("<checkout_id>")
                .token("<secret_token>")
                .build();

        Mockito.when(updatePaymentLogic.processLogic(updatePaymentRequest))
                .thenThrow(SecretTokenInvalidException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/payment")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatePaymentRequest)))
                .andExpect(status().is(498));
    }
}