package id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.AppAlreadyInListException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class AppNotInListValidatorTest {
    @Spy
    private AppNotInListValidator appNotInListValidator;
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
        doReturn(true).when(appNotInListValidator).appNotInList(request);
        Assertions.assertThat(appNotInListValidator.isValid(request)).isTrue();
    }

    @Test
    void testIsValidReturnTrueWithNextValidatorNotNull() {
        AppNotInListValidator nextValidator = Mockito.spy(AppNotInListValidator.class);
        appNotInListValidator.setNextValidator(nextValidator);

        doReturn(true).when(appNotInListValidator).appNotInList(request);
        doReturn(true).when(nextValidator).appNotInList(request);

        Assertions.assertThat(appNotInListValidator.isValid(request)).isTrue();
    }

    @Test
    void testIsValidThrowError() {
        doReturn(false).when(appNotInListValidator).appNotInList(request);
        Assertions.assertThatThrownBy(() -> appNotInListValidator.isValid(request))
                .isInstanceOf(AppAlreadyInListException.class)
                .hasMessage(String.format("App %s with id:%s already in user's bought list",
                        request.getName(),
                        request.getId()));
    }
}