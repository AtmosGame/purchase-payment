package id.ac.ui.cs.advprog.purchasepayment.validation.addsecrettoken.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.AddSecretTokenRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddSecretTokenRequestValidatorTest {

    private AddSecretTokenValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new AddSecretTokenValidator();
    }

    @Test
    void testIsValidWithValidRequest() {
        AddSecretTokenRequest request = AddSecretTokenRequest.builder()
                .tokenName("valid_token_name")
                .build();
        assertTrue(validator.isValid(request));
    }

    @Test
    void testIsValidWithEmptyTokenName() {
        AddSecretTokenRequest request = AddSecretTokenRequest.builder()
                .tokenName("")
                .build();
        RuntimeException exception = assertThrows(RuntimeException.class, () -> validator.isValid(request));
        assertEquals("Token cannot be empty", exception.getMessage());
    }
}
