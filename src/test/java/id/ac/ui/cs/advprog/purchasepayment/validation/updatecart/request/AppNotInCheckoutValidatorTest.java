package id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.AppAlreadyInCheckoutException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class AppNotInCheckoutValidatorTest {
    @Spy
    private AppNotInCheckoutValidator appNotInCheckoutValidator;
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
        UpdateCartRequestDataValidator nextValidator = Mockito.spy(UpdateCartRequestDataValidator.class);
        appNotInCheckoutValidator.setNextValidator(nextValidator);

        doReturn(true).when(appNotInCheckoutValidator).appNotInCheckout(request);
        doReturn(true).when(nextValidator).requestDataValid(request);

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
}