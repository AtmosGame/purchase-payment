package id.ac.ui.cs.advprog.purchasepayment.validation.updatepayment.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.SecretTokenInvalidException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class SecretTokenValidatorTest {
    @Spy
    private SecretTokenValidator secretTokenValidator;
    private UpdatePaymentRequest request;

    @BeforeEach
    void setUp() {
        request = UpdatePaymentRequest.builder()
                .id("<checkout_id>")
                .token("<secret_token>")
                .build();
    }

    @Test
    void testIsValidReturnTrueWithNextValidatorNull() {
        doReturn(true).when(secretTokenValidator).secretTokenValid(request);
        Assertions.assertThat(secretTokenValidator.isValid(request)).isTrue();
    }

    @Test
    void testIsValidReturnTrueWithNextValidatorNotNull() {
        SecretTokenValidator  nextValidator = Mockito.spy(SecretTokenValidator .class);
        secretTokenValidator.setNextValidator(nextValidator);

        doReturn(true).when(secretTokenValidator).secretTokenValid(request);
        doReturn(true).when(nextValidator).secretTokenValid(request);

        Assertions.assertThat(secretTokenValidator.isValid(request)).isTrue();
    }

    @Test
    void testIsValidThrowError() {
        doReturn(false).when(secretTokenValidator).secretTokenValid(request);
        Assertions.assertThatThrownBy(() -> secretTokenValidator.isValid(request))
                .isInstanceOf(SecretTokenInvalidException.class)
                .hasMessage(String.format(String.format("Secret token %s invalid", request.getToken())));
    }
}
