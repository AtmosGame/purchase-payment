package id.ac.ui.cs.advprog.purchasepayment.validation.checkpurchasedapp.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.AddSecretTokenRequest;
import id.ac.ui.cs.advprog.purchasepayment.dto.CheckPurchasedRequest;
import id.ac.ui.cs.advprog.purchasepayment.dto.CheckPurchasedResponse;
import id.ac.ui.cs.advprog.purchasepayment.validation.checkpurchasedapps.request.CheckPurchasedRequestValidator;
import id.ac.ui.cs.advprog.purchasepayment.validation.checkpurchasedapps.response.CheckPurchasedResponseValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckPurchasedRequestValidatorTest {

    private CheckPurchasedRequestValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new CheckPurchasedRequestValidator();
    }

    @Test
    void testIsValidWithValidRequest() {
        CheckPurchasedRequest request = CheckPurchasedRequest.builder()
                .appId("appId")
                .username("username")
                .build();
        assertTrue(validator.isValid(request));
    }

    @Test
    void testIsValidWithEmptyAppId() {
        CheckPurchasedRequest request = CheckPurchasedRequest.builder()
                .appId("")
                .username("username")
                .build();
        RuntimeException exception = assertThrows(RuntimeException.class, () -> validator.isValid(request));
        assertEquals("App with this id is not available", exception.getMessage());
    }

    @Test
    void testIsValidWithEmptyUsername() {
        CheckPurchasedRequest request = CheckPurchasedRequest.builder()
                .appId("appId")
                .username("")
                .build();
        RuntimeException exception = assertThrows(RuntimeException.class, () -> validator.isValid(request));
        assertEquals("Username is not available", exception.getMessage());
    }
}
