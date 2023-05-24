package id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.AppAlreadyInCartException;
import id.ac.ui.cs.advprog.purchasepayment.ports.CartDetailsRepository;
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
class AppNotInCartValidatorTest {
    @Spy
    @InjectMocks
    private AppNotInCartValidator appNotInCartValidator;
    @Mock
    private CartDetailsRepository cartDetailsRepository;
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
        doReturn(true).when(appNotInCartValidator).appNotInCart(request);
        Assertions.assertThat(appNotInCartValidator.isValid(request)).isTrue();
    }

    @Test
    void testIsValidReturnTrueWithNextValidatorNotNull() {
        AppNotInCartValidator nextValidator = Mockito.spy(AppNotInCartValidator.class);
        appNotInCartValidator.setNextValidator(nextValidator);

        doReturn(true).when(appNotInCartValidator).appNotInCart(request);
        doReturn(true).when(nextValidator).appNotInCart(request);

        Assertions.assertThat(appNotInCartValidator.isValid(request)).isTrue();
    }

    @Test
    void testIsValidThrowError() {
        doReturn(false).when(appNotInCartValidator).appNotInCart(request);
        Assertions.assertThatThrownBy(() -> appNotInCartValidator.isValid(request))
                .isInstanceOf(AppAlreadyInCartException.class)
                .hasMessage(String.format("App %s with id:%s already exist in cart",
                        request.getName(),
                        request.getId()));
    }

    @Test
    void testAppNotInCartIsFalse() {
        doReturn(true).when(cartDetailsRepository).existsByCartUsernameAndAppId(request.getUsername(), request.getId());
        appNotInCartValidator.appNotInCart(request);
        verify(cartDetailsRepository, times(1)).existsByCartUsernameAndAppId(request.getUsername(), request.getId());
    }

    @Test
    void testAppNotInCartIsTrue() {
        doReturn(false).when(cartDetailsRepository).existsByCartUsernameAndAppId(request.getUsername(), request.getId());
        appNotInCartValidator.appNotInCart(request);
        verify(cartDetailsRepository, times(1)).existsByCartUsernameAndAppId(request.getUsername(), request.getId());
    }
}