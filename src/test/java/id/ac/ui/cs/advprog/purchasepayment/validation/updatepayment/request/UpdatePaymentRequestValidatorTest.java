package id.ac.ui.cs.advprog.purchasepayment.validation.updatepayment.request;

import id.ac.ui.cs.advprog.purchasepayment.dto.UpdatePaymentRequest;
import id.ac.ui.cs.advprog.purchasepayment.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UpdatePaymentRequestValidatorTest {
    private Validator<UpdatePaymentRequest> secretTokenValidator;
    private Validator<UpdatePaymentRequest> checkoutNotExpiredValidator;
    private UpdatePaymentRequest request;

    @BeforeEach
    void setUp() {
        UpdatePaymentRequestFactory factory = new UpdatePaymentRequestFactoryImpl();
        secretTokenValidator = factory.createSecretTokenValidator();
        checkoutNotExpiredValidator = factory.createCheckoutNotExpiredValidator();

        request = UpdatePaymentRequest.builder()
                .id("<checkout_id>")
                .token("<secret_token>")
                .build();
    }

    @Test
    void testIsValidWithoutNextValidator() {
        Assertions.assertAll(
                "Valid with next validator is null",
                () -> {
                    assertThat(secretTokenValidator.isValid(request)).isTrue();
                    assertThat(checkoutNotExpiredValidator.isValid(request)).isTrue();
                }
        );
    }

    @Test
    void testIsValidWithNextValidator() {
        secretTokenValidator.setNextValidator(checkoutNotExpiredValidator);
        checkoutNotExpiredValidator.setNextValidator(new CheckoutNotExpiredValidator());

        Validator<UpdatePaymentRequest> validator = secretTokenValidator;

        Assertions.assertAll(
                "Valid with next validator is not null",
                () -> {
                    assertThat(validator.isValid(request)).isTrue();
                    assertThat(secretTokenValidator.isValid(request)).isTrue();
                    assertThat(checkoutNotExpiredValidator.isValid(request)).isTrue();
                }
        );
    }
}
