package id.ac.ui.cs.advprog.purchasepayment.validation.checkpurchasedapp.response;

import id.ac.ui.cs.advprog.purchasepayment.dto.CheckPurchasedResponse;
import id.ac.ui.cs.advprog.purchasepayment.validation.checkpurchasedapps.response.CheckPurchasedResponseValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckPurchasedResponseValidatorTest {

    private CheckPurchasedResponseValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new CheckPurchasedResponseValidator();
    }

    @Test
    void testIsValidWithValidRequest() {
        CheckPurchasedResponse response = CheckPurchasedResponse.builder()
                .isPurchased(true)
                .build();
        assertTrue(validator.isValid(response));
    }

    @Test
    void testIsValidWithEmptyBoolean() {
        CheckPurchasedResponse response = CheckPurchasedResponse.builder()
                .isPurchased(null)
                .build();
        RuntimeException exception = assertThrows(RuntimeException.class, () -> validator.isValid(response));
    }
}
