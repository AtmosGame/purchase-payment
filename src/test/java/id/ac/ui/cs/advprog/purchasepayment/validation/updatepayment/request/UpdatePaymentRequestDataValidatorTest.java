package id.ac.ui.cs.advprog.purchasepayment.validation.updatepayment.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.exceptions.RequestDataInvalidException;
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
class UpdatePaymentRequestDataValidatorTest {
    @Spy
    @InjectMocks
    private UpdatePaymentRequestDataValidator updatePaymentRequestDataValidator;
    @Mock
    private CheckoutRepository checkoutRepository;
    private UpdatePaymentRequest request;

    @BeforeEach
    void setUp() {
        request = UpdatePaymentRequest.builder()
                .id("1")
                .token("<secret_token>")
                .build();
    }

    @Test
    void testIsValidReturnTrueWithNextValidatorNull() {
        doReturn(true).when(updatePaymentRequestDataValidator).checkoutDataIsValid(request);
        Assertions.assertThat(updatePaymentRequestDataValidator.isValid(request)).isTrue();
    }

    @Test
    void testIsValidReturnTrueWithNextValidatorNotNull() {
        UpdatePaymentRequestDataValidator  nextValidator = Mockito.spy(UpdatePaymentRequestDataValidator.class);
        updatePaymentRequestDataValidator.setNextValidator(nextValidator);

        doReturn(true).when(updatePaymentRequestDataValidator).checkoutDataIsValid(request);
        doReturn(true).when(nextValidator).checkoutDataIsValid(request);

        Assertions.assertThat(updatePaymentRequestDataValidator.isValid(request)).isTrue();
    }

    @Test
    void testIsValidThrowError() {
        doReturn(false).when(updatePaymentRequestDataValidator).checkoutDataIsValid(request);
        Assertions.assertThatThrownBy(() -> updatePaymentRequestDataValidator.isValid(request))
                .isInstanceOf(RequestDataInvalidException.class);
    }

    @Test
    void testCheckoutDataIsValidIsFalse() {
        doReturn(true).when(checkoutRepository).existsById(Integer.valueOf(request.getId()));
        updatePaymentRequestDataValidator.checkoutDataIsValid(request);
        verify(checkoutRepository, times(1)).existsById(Integer.valueOf(request.getId()));
    }

    @Test
    void testCheckoutDataIsValidIsTrue() {
        doReturn(false).when(checkoutRepository).existsById(Integer.valueOf(request.getId()));
        updatePaymentRequestDataValidator.checkoutDataIsValid(request);
        verify(checkoutRepository, times(1)).existsById(Integer.valueOf(request.getId()));
    }
}