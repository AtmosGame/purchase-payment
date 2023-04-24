package id.ac.ui.cs.advprog.purchasepayment.validation.updatecart.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdateCartRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.RequestDataIsNotValidException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UpdateCartRequestDataValidatorTest {
    @Spy
    private UpdateCartRequestDataValidator updateCartRequestDataValidator;
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
        doReturn(true).when(updateCartRequestDataValidator).requestDataValid(request);
        Assertions.assertThat(updateCartRequestDataValidator.isValid(request)).isTrue();
    }

    @Test
    void testIsValidReturnTrueWithNextValidatorNotNull() {
        UpdateCartRequestDataValidator nextValidator = Mockito.spy(UpdateCartRequestDataValidator.class);
        updateCartRequestDataValidator.setNextValidator(nextValidator);

        doReturn(true).when(updateCartRequestDataValidator).requestDataValid(request);
        doReturn(true).when(nextValidator).requestDataValid(request);

        Assertions.assertThat(updateCartRequestDataValidator.isValid(request)).isTrue();
    }

    @Test
    void testIsValidThrowError() {
        doReturn(false).when(updateCartRequestDataValidator).requestDataValid(request);
        Assertions.assertThatThrownBy(() -> updateCartRequestDataValidator.isValid(request))
                .isInstanceOf(RequestDataIsNotValidException.class)
                .hasMessage("Request data is not valid");
    }
}