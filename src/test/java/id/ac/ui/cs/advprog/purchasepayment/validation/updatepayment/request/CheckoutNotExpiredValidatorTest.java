package id.ac.ui.cs.advprog.purchasepayment.validation.updatepayment.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.CheckoutIsExpiredException;
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
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CheckoutNotExpiredValidatorTest {
    @Spy
    @InjectMocks
    private CheckoutNotExpiredValidator checkoutNotExpiredValidator;
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
        doReturn(true).when(checkoutNotExpiredValidator).checkoutNotExpired(request);
        Assertions.assertThat(checkoutNotExpiredValidator.isValid(request)).isTrue();
    }

    @Test
    void testIsValidReturnTrueWithNextValidatorNotNull() {
        CheckoutNotExpiredValidator nextValidator = Mockito.spy(CheckoutNotExpiredValidator.class);
        checkoutNotExpiredValidator.setNextValidator(nextValidator);

        doReturn(true).when(checkoutNotExpiredValidator).checkoutNotExpired(request);
        doReturn(true).when(nextValidator).checkoutNotExpired(request);

        Assertions.assertThat(checkoutNotExpiredValidator.isValid(request)).isTrue();
    }

    @Test
    void testIsValidThrowError() {
        doReturn(false).when(checkoutNotExpiredValidator).checkoutNotExpired(request);
        Assertions.assertThatThrownBy(() -> checkoutNotExpiredValidator.isValid(request))
                .isInstanceOf(CheckoutIsExpiredException.class)
                .hasMessage(String.format("Checkout with %s already expired", request.getId()));
    }

    @Test
    void testCheckoutNotExpiredIsFalse() {
        Checkout checkoutExpired = Checkout.builder()
                .statusPembayaran("Expired")
                .build();
        Optional<Checkout> checkoutExpiredOptional = Optional.of(checkoutExpired);
        doReturn(checkoutExpiredOptional).when(checkoutRepository).findById(anyInt());

        request.setId("1");
        var result = checkoutNotExpiredValidator.checkoutNotExpired(request);
        Assertions.assertThat(result).isFalse();
    }

    @Test
    void testCheckoutNotExpiredIsTrue() {
        Checkout checkoutExpired = Checkout.builder()
                .statusPembayaran("Success")
                .build();
        Optional<Checkout> checkoutExpiredOptional = Optional.of(checkoutExpired);
        doReturn(checkoutExpiredOptional).when(checkoutRepository).findById(anyInt());

        request.setId("1");
        var result = checkoutNotExpiredValidator.checkoutNotExpired(request);
        Assertions.assertThat(result).isTrue();
    }
}
