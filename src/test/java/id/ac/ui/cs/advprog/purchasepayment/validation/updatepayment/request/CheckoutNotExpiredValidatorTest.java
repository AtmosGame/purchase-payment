package id.ac.ui.cs.advprog.purchasepayment.validation.updatepayment.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.CheckoutIsExpiredException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CheckoutNotExpiredValidatorTest {
    @Spy
    private CheckoutNotExpiredValidator checkoutNotExpiredValidator;
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
}
