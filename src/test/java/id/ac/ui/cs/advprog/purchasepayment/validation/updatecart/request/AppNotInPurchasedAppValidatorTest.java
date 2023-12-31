package id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.AppAlreadyInPurchasedAppException;
import id.ac.ui.cs.advprog.purchasepayment.ports.PurchasedAppRepository;
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
class AppNotInPurchasedAppValidatorTest {
    @Spy
    @InjectMocks
    private AppNotInPurchasedAppValidator appNotInPurchasedAppValidator;
    @Mock
    private PurchasedAppRepository purchasedAppRepository;
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
        doReturn(true).when(appNotInPurchasedAppValidator).appNotInPurchasedApp(request);
        Assertions.assertThat(appNotInPurchasedAppValidator.isValid(request)).isTrue();
    }

    @Test
    void testIsValidReturnTrueWithNextValidatorNotNull() {
        AppNotInPurchasedAppValidator nextValidator = Mockito.spy(AppNotInPurchasedAppValidator.class);
        appNotInPurchasedAppValidator.setNextValidator(nextValidator);

        doReturn(true).when(appNotInPurchasedAppValidator).appNotInPurchasedApp(request);
        doReturn(true).when(nextValidator).appNotInPurchasedApp(request);

        Assertions.assertThat(appNotInPurchasedAppValidator.isValid(request)).isTrue();
    }

    @Test
    void testIsValidThrowError() {
        doReturn(false).when(appNotInPurchasedAppValidator).appNotInPurchasedApp(request);
        Assertions.assertThatThrownBy(() -> appNotInPurchasedAppValidator.isValid(request))
                .isInstanceOf(AppAlreadyInPurchasedAppException.class)
                .hasMessage(String.format("App %s with id:%s already in user's purchased app",
                        request.getName(),
                        request.getId()));
    }

    @Test
    void testAppNotInCartIsFalse() {
        doReturn(true).when(purchasedAppRepository).existsByUsernameAndAppId(request.getUsername(), request.getId());
        appNotInPurchasedAppValidator.appNotInPurchasedApp(request);
        verify(purchasedAppRepository, times(1)).existsByUsernameAndAppId(request.getUsername(), request.getId());
    }

    @Test
    void testAppNotInCartIsTrue() {
        doReturn(false).when(purchasedAppRepository).existsByUsernameAndAppId(request.getUsername(), request.getId());
        appNotInPurchasedAppValidator.appNotInPurchasedApp(request);
        verify(purchasedAppRepository, times(1)).existsByUsernameAndAppId(request.getUsername(), request.getId());
    }
}