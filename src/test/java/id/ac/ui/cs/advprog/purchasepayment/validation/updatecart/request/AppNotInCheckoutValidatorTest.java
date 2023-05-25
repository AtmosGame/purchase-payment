package id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.AppAlreadyInCheckoutException;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppNotInCheckoutValidatorTest {
    @Spy
    @InjectMocks
    private AppNotInCheckoutValidator appNotInCheckoutValidator;
    @Mock
    private CheckoutRepository checkoutRepository;
    private UpdateCartRequest request;

    @BeforeEach
    void setUp() {
        request = UpdateCartRequest.builder()
                .id("<app_id>")
                .name("<app_name>")
                .price(0.0)
                .username("<requestor_username>")
                .build();
    }

    @Test
    void testIsValidReturnTrueWithNextValidatorNull() {
        doReturn(true).when(appNotInCheckoutValidator).appNotInCheckout(request);
        Assertions.assertThat(appNotInCheckoutValidator.isValid(request)).isTrue();
    }

    @Test
    void testIsValidReturnTrueWithNextValidatorNotNull() {
        AppNotInCheckoutValidator  nextValidator = Mockito.spy(AppNotInCheckoutValidator .class);
        appNotInCheckoutValidator.setNextValidator(nextValidator);

        doReturn(true).when(appNotInCheckoutValidator).appNotInCheckout(request);
        doReturn(true).when(nextValidator).appNotInCheckout(request);

        Assertions.assertThat(appNotInCheckoutValidator.isValid(request)).isTrue();
    }

    @Test
    void testIsValidThrowError() {
        doReturn(false).when(appNotInCheckoutValidator).appNotInCheckout(request);
        Assertions.assertThatThrownBy(() -> appNotInCheckoutValidator.isValid(request))
                .isInstanceOf(AppAlreadyInCheckoutException.class)
                .hasMessage(String.format("App %s with id:%s already in checkout process",
                        request.getName(),
                        request.getId()));
    }

    @Test
    void testAppNotInCartIsFalse() {
        doReturn(true).when(checkoutRepository).existsAppInUserActiveCheckout(request.getUsername(), request.getId());
        appNotInCheckoutValidator.appNotInCheckout(request);
        verify(checkoutRepository, times(1)).existsAppInUserActiveCheckout(request.getUsername(), request.getId());
    }

    @Test
    void testAppNotInCartIsTrue() {
        doReturn(false).when(checkoutRepository).existsAppInUserActiveCheckout(request.getUsername(), request.getId());
        appNotInCheckoutValidator.appNotInCheckout(request);
        verify(checkoutRepository, times(1)).existsAppInUserActiveCheckout(request.getUsername(), request.getId());
    }
}