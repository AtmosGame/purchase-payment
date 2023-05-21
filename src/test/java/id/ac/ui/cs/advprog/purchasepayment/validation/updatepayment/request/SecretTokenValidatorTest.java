package id.ac.ui.cs.advprog.purchasepayment.validation.updatepayment.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.SecretTokenInvalidException;
import id.ac.ui.cs.advprog.purchasepayment.ports.SecretTokenRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecretTokenValidatorTest {
    @Spy
    @InjectMocks
    private SecretTokenValidator secretTokenValidator;
    @Mock
    private SecretTokenRepository secretTokenRepository;
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

    @Test
    void testSecretTokenValidIsFalse() {
        doReturn(true).when(secretTokenRepository).existsByTokenName(request.getToken());
        secretTokenValidator.secretTokenValid(request);
        verify(secretTokenRepository, times(1)).existsByTokenName(request.getToken());
    }

    @Test
    void testSecretTokenValidIsTrue() {
        doReturn(false).when(secretTokenRepository).existsByTokenName(request.getToken());
        secretTokenValidator.secretTokenValid(request);
        verify(secretTokenRepository, times(1)).existsByTokenName(request.getToken());
    }

}
