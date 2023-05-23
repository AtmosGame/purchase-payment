package id.ac.ui.cs.advprog.purchasepayment.validation.updatepayment.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.CheckoutIsNotActiveException;
import id.ac.ui.cs.advprog.purchasepayment.models.Checkout;
import id.ac.ui.cs.advprog.purchasepayment.ports.CheckoutRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CheckoutIsActiveValidatorTest {
    @Spy
    @InjectMocks
    private CheckoutIsActiveValidator checkoutIsActiveValidator;
    @Mock
    private CheckoutRepository checkoutRepository;
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
        doReturn(true).when(checkoutIsActiveValidator).checkoutIsActive(request);
        Assertions.assertThat(checkoutIsActiveValidator.isValid(request)).isTrue();
    }

    @Test
    void testIsValidReturnTrueWithNextValidatorNotNull() {
        CheckoutIsActiveValidator nextValidator = Mockito.spy(CheckoutIsActiveValidator.class);
        checkoutIsActiveValidator.setNextValidator(nextValidator);

        doReturn(true).when(checkoutIsActiveValidator).checkoutIsActive(request);
        doReturn(true).when(nextValidator).checkoutIsActive(request);

        Assertions.assertThat(checkoutIsActiveValidator.isValid(request)).isTrue();
    }

    @Test
    void testIsValidThrowError() {
        doReturn(false).when(checkoutIsActiveValidator).checkoutIsActive(request);
        Assertions.assertThatThrownBy(() -> checkoutIsActiveValidator.isValid(request))
                .isInstanceOf(CheckoutIsNotActiveException.class)
                .hasMessage(String.format("Checkout with %s not in active/waiting state", request.getId()));
    }

    @Test
    void testCheckoutIsActiveIsFalse() {
        Checkout checkoutExpired = Checkout.builder()
                .statusPembayaran("Expired")
                .build();
        Optional<Checkout> checkoutExpiredOptional = Optional.of(checkoutExpired);
        doReturn(checkoutExpiredOptional).when(checkoutRepository).findById(anyInt());

        request.setId("1");
        var result = checkoutIsActiveValidator.checkoutIsActive(request);
        Assertions.assertThat(result).isFalse();
    }
    @Test
    void testCheckoutIsActiveIsFalseAndOptionalIsEmpty() {
        doReturn(Optional.empty()).when(checkoutRepository).findById(anyInt());

        request.setId("1");
        var result = checkoutIsActiveValidator.checkoutIsActive(request);
        Assertions.assertThat(result).isFalse();
    }

    @Test
    void testCheckoutIsActiveIsTrue() {
        Checkout checkoutExpired = Checkout.builder()
                .statusPembayaran("Menunggu Pembayaran")
                .build();
        Optional<Checkout> checkoutExpiredOptional = Optional.of(checkoutExpired);
        doReturn(checkoutExpiredOptional).when(checkoutRepository).findById(anyInt());

        request.setId("1");
        var result = checkoutIsActiveValidator.checkoutIsActive(request);
        Assertions.assertThat(result).isTrue();
    }
}
